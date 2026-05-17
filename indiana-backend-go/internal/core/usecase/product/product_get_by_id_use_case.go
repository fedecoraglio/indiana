package usecase

import (
	"context"
	"indiana-backend-go/internal/core/domain"
	"indiana-backend-go/internal/core/port"
)

type ProductGetByIDUseCase struct {
	productRepository port.ProductRepository
}

func NewProductGetByIDUseCase(productRepository port.ProductRepository) *ProductGetByIDUseCase {
	return &ProductGetByIDUseCase{productRepository: productRepository}
}

func (uc *ProductGetByIDUseCase) GetProductByID(ctx context.Context, id int64) (*domain.Product, error) {
	product, err := uc.productRepository.GetProductByID(ctx, id)
	if err != nil {
		return nil, err
	}
	return product, nil
}
