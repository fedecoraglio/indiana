package usecase

import (
	"context"
	"indiana-backend-go/internal/core/domain"
	"indiana-backend-go/internal/core/policy"
	"indiana-backend-go/internal/core/port"
)

type ProductUpdateUseCase struct {
	productRepository   port.ProductRepository
	productUpdatePolicy *policy.ProductUpdatePolicy
}

func NewProductUpdateUseCase(pr port.ProductRepository, pp *policy.ProductUpdatePolicy) *ProductUpdateUseCase {
	return &ProductUpdateUseCase{productRepository: pr, productUpdatePolicy: pp}
}

func (p *ProductUpdateUseCase) PathProduct(ctx context.Context, product *domain.Product) (*domain.Product, error) {
	if err := p.productUpdatePolicy.Execute(ctx, product); err != nil {
		return nil, err
	}

	updatedProduct, err := p.productRepository.PathProduct(ctx, *product)
	if err != nil {
		return nil, err
	}

	return updatedProduct, nil
}
