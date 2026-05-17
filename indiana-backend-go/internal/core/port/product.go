package port

import (
	"context"
	"indiana-backend-go/internal/core/domain"
)

type ProductRepository interface {
	CreateProduct(ctx context.Context, product domain.Product) (*domain.Product, error)
	PathProduct(ctx context.Context, product domain.Product) (*domain.Product, error)
	GetProductByCodeOrBarCodeOrName(ctx context.Context, code string, barCode string, name string) ([]domain.Product, error)
	GetProductByID(ctx context.Context, id int64) (*domain.Product, error)
	ListProducts(ctx context.Context, page uint64, limit uint64) ([]domain.Product, error)
}
