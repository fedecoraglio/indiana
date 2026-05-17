package usecase

import (
	"context"
	"indiana-backend-go/internal/core/domain"
	"indiana-backend-go/internal/core/port"
)

type ProductListUseCase struct {
	productRepository port.ProductRepository
}

func NewProductListUseCase(productRepository port.ProductRepository) *ProductListUseCase {
	return &ProductListUseCase{
		productRepository: productRepository,
	}
}

func (uc *ProductListUseCase) ListProducts(ctx context.Context, page uint64, limit uint64) ([]domain.Product, error) {
	return uc.productRepository.ListProducts(ctx, page, limit)
}
