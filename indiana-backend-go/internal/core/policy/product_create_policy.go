package policy

import (
	"context"
	"errors"
	"indiana-backend-go/internal/core/domain"
	"indiana-backend-go/internal/core/port"
)

type ProductCreatePolicy struct {
	productRepository port.ProductRepository
}

func NewProductCreatePolicy(productRepository port.ProductRepository) *ProductCreatePolicy {
	return &ProductCreatePolicy{productRepository: productRepository}
}

func (productPolicy *ProductCreatePolicy) Execute(ctx context.Context, product *domain.Product) error {
	if product == nil {
		return errors.New("product is nil")
	}

	if product.Name == "" {
		return errors.New("product name is empty")
	}

	if product.BarCode == "" {
		return errors.New("product barCode is empty")
	}

	if product.Code == "" {
		return errors.New("product code is empty")
	}

	products, err := productPolicy.productRepository.GetProductByCodeOrBarCodeOrName(ctx, product.Code,
		product.BarCode, product.Name)
	if err != nil {
		return errors.New("error getting product by code")
	}

	if len(products) > 0 {
		return errors.New("product already exists")
	}
	return nil
}
