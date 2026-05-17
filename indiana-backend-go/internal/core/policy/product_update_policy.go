package policy

import (
	"context"
	"errors"
	"indiana-backend-go/internal/core/domain"
	"indiana-backend-go/internal/core/port"
)

type ProductUpdatePolicy struct {
	productRepository port.ProductRepository
}

func NewProductUpdatePolicy(productRepository port.ProductRepository) *ProductUpdatePolicy {
	return &ProductUpdatePolicy{productRepository: productRepository}
}

func (pp *ProductUpdatePolicy) Execute(ctx context.Context, product *domain.Product) error {
	if product == nil {
		return errors.New("product is nil")
	}

	if product.ID == 0 {
		return errors.New("product is is nil")
	}

	products, err := pp.productRepository.GetProductByCodeOrBarCodeOrName(ctx, product.Code,
		product.BarCode, product.Name)
	if err != nil {
		return errors.New("error getting product by code")
	}
	if len(products) > 0 {
		if product.ID != products[0].ID {
			return errors.New("product already exists")
		}
		return nil
	}
	return errors.New("product not found")
}
