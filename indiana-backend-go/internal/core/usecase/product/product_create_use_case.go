package usecase

import (
	"context"
	"indiana-backend-go/internal/core/domain"
	"indiana-backend-go/internal/core/policy"
	"indiana-backend-go/internal/core/port"
)

type ProductCreateUseCase struct {
	productRepository   port.ProductRepository
	productCreatePolicy *policy.ProductCreatePolicy
}

func NewProductCreateUseCase(pr port.ProductRepository, pp *policy.ProductCreatePolicy) *ProductCreateUseCase {
	return &ProductCreateUseCase{productRepository: pr, productCreatePolicy: pp}
}

func (c *ProductCreateUseCase) CreateProduct(ctx context.Context, product domain.Product) (*domain.Product, error) {
	if err := c.productCreatePolicy.Execute(ctx, &product); err != nil {
		return nil, err
	}

	newProduct, err := c.productRepository.CreateProduct(ctx, product)
	if err != nil {
		return nil, err
	}

	return newProduct, nil
}
