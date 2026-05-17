package main

import (
	"context"
	"fmt"
	"indiana-backend-go/internal/adapter/config"
	"indiana-backend-go/internal/adapter/handler/http"
	"indiana-backend-go/internal/adapter/logger"
	"indiana-backend-go/internal/adapter/store/mysql"
	"indiana-backend-go/internal/adapter/store/mysql/repository"
	"indiana-backend-go/internal/core/policy"
	"indiana-backend-go/internal/core/usecase/product"
	"log/slog"
	"os"
)

func main() {
	// Load environment variables
	configApp, err := config.New()
	if err != nil {
		slog.Error("Error loading environment variables", "error", err)
		os.Exit(1)
	}

	// Set logger
	logger.Set(configApp.App)

	slog.Info("Starting the application", "app", configApp.App.Name, "env", configApp.App.Env)

	// Init database
	ctx := context.Background()
	db, err := mysql.New(ctx, configApp.DB)
	if err != nil {
		slog.Error("Error initializing database connection", "error", err)
		os.Exit(1)
	}
	defer db.Close()

	slog.Info("Successfully connected to the database", "db", configApp.DB.Connection)

	// Migrate database
	err = db.Migrate()
	if err != nil {
		slog.Error("Error migrating database", "error", err)
		os.Exit(1)
	}

	slog.Info("Successfully migrated the database")

	// Dependency injection
	// Product
	productRepository := repository.NewProductRepository(db)
	// Product policies
	productCreatePolicy := policy.NewProductCreatePolicy(productRepository)
	productUpdatePolicy := policy.NewProductUpdatePolicy(productRepository)
	// Product use cases
	productCreateUseCase := usecase.NewProductCreateUseCase(productRepository, productCreatePolicy)
	productUpdateUseCase := usecase.NewProductUpdateUseCase(productRepository, productUpdatePolicy)
	productGetByIdUseCase := usecase.NewProductGetByIDUseCase(productRepository)
	productListUseCase := usecase.NewProductListUseCase(productRepository)
	// Product handler
	productHandler := http.NewProductHandler(productCreateUseCase, productUpdateUseCase, productGetByIdUseCase, productListUseCase)

	// Init router
	router, err := http.NewRouter(
		configApp.HTTP,
		*productHandler,
	)
	if err != nil {
		slog.Error("Error initializing router", "error", err)
		os.Exit(1)
	}

	// Start server
	listenAddr := fmt.Sprintf("%s:%s", configApp.HTTP.URL, configApp.HTTP.Port)
	slog.Info("Starting the HTTP server", "listen_address", listenAddr)
	err = router.Serve(listenAddr)
	if err != nil {
		slog.Error("Error starting the HTTP server", "error", err)
		os.Exit(1)
	}
}
