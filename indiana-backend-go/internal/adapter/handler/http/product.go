package http

import (
	"github.com/gin-gonic/gin"
	"indiana-backend-go/internal/core/domain"
	"indiana-backend-go/internal/core/usecase/product"
)

// ProductHandler represents the HTTP handler for product-related requests
type ProductHandler struct {
	createUseCase  *usecase.ProductCreateUseCase
	updateUseCase  *usecase.ProductUpdateUseCase
	getByIdUseCase *usecase.ProductGetByIDUseCase
	listUseCase    *usecase.ProductListUseCase
}

// NewProductHandler creates a new ProductHandler instance
func NewProductHandler(
	createUseCase *usecase.ProductCreateUseCase,
	updateUseCase *usecase.ProductUpdateUseCase,
	getByIdUseCase *usecase.ProductGetByIDUseCase,
	listUseCase *usecase.ProductListUseCase) *ProductHandler {

	return &ProductHandler{createUseCase, updateUseCase,
		getByIdUseCase, listUseCase}
}

// createProductRequest represents a request body for creating a new product
type createProductRequest struct {
	Code         string  `json:"code" binding:"required" example:"it must be unique"`
	BarCode      string  `json:"bar_code" binding:"required" example:"it must be unique"`
	Name         string  `json:"name" binding:"required" example:"it must be unique"`
	Description  string  `json:"description" binding:"required" example:"any string description"`
	PackedWeight float64 `json:"packed_weight" binding:"required,min=0" example:"50000"`
	PackedWidth  int64   `json:"packed_width" binding:"required,min=0" example:"50000"`
	PackedDepth  int64   `json:"packed_depth" binding:"required,min=0" example:"50000"`
}

func (productHandler *ProductHandler) CreateProduct(ctx *gin.Context) {
	var req createProductRequest
	if err := ctx.ShouldBindJSON(&req); err != nil {
		validationError(ctx, err)
		return
	}

	product := domain.Product{
		Code:         req.Code,
		BarCode:      req.BarCode,
		Name:         req.Name,
		Description:  req.Description,
		PackedWeight: req.PackedWeight,
		PackedWidth:  req.PackedWeight,
		PackedDepth:  req.PackedWeight,
	}

	newProduct, err := productHandler.createUseCase.CreateProduct(ctx, product)
	if err != nil {
		handleError(ctx, err)
		return
	}

	handleSuccess(ctx, gin.H{"product": newProductResponse(newProduct)})
}

// getProductRequest represents a request body for retrieving a product
type getProductRequest struct {
	ID int64 `uri:"id" binding:"required,min=1" example:"1"`
}

func (productHandler *ProductHandler) GetProduct(ctx *gin.Context) {
	var req getProductRequest
	if err := ctx.ShouldBindUri(&req); err != nil {
		validationError(ctx, err)
		return
	}

	product, err := productHandler.getByIdUseCase.GetProductByID(ctx, req.ID)
	if err != nil {
		handleError(ctx, err)
		return
	}

	rsp := newProductResponse(product)

	handleSuccess(ctx, rsp)
}

type pathProductRequest struct {
	ID           int64    `uri:"id" binding:"required,min=1" example:"1"`
	Code         *string  `json:"code"`
	BarCode      *string  `json:"bar_code"`
	Name         *string  `json:"name"`
	Description  *string  `json:"description"`
	PackedWeight *float64 `json:"packed_weight"`
	PackedWidth  *int64   `json:"packed_width"`
	PackedDepth  *int64   `json:"packed_depth"`
}

func newPathProductRequest(pathProductReq pathProductRequest, product *domain.Product) *domain.Product {
	if pathProductReq.Name != nil && len(*pathProductReq.Name) > 0 {
		product.Name = *pathProductReq.Name
	}

	if pathProductReq.Description != nil && len(*pathProductReq.Description) > 0 {
		product.Description = *pathProductReq.Description
	}

	if pathProductReq.PackedWeight != nil {
		product.PackedWeight = *pathProductReq.PackedWeight
	}

	if pathProductReq.PackedWidth != nil {
		product.PackedWidth = float64(*pathProductReq.PackedWidth)
	}

	if pathProductReq.PackedDepth != nil {
		product.PackedDepth = float64(*pathProductReq.PackedDepth)
	}
	return product
}

func (productHandler *ProductHandler) PathProduct(ctx *gin.Context) {
	var req pathProductRequest

	if err := ctx.ShouldBindUri(&req); err != nil {
		validationError(ctx, err)
		return
	}

	if err := ctx.ShouldBindJSON(&req); err != nil {
		validationError(ctx, err)
		return
	}

	product, err := productHandler.getByIdUseCase.GetProductByID(ctx, req.ID)
	if err != nil {
		handleError(ctx, err)
		return
	}

	product = newPathProductRequest(req, product)

	updateProduct, err := productHandler.updateUseCase.PathProduct(ctx, product)
	if err != nil {
		handleError(ctx, err)
		return
	}

	updateResponse := newProductResponse(updateProduct)
	handleSuccess(ctx, updateResponse)
}

type listProductsRequest struct {
	Page  uint64 `form:"page" binding:"required,min=1"`
	Limit uint64 `form:"limit" binding:"required,min=1,max=100"`
}

func (productHandler *ProductHandler) ListProducts(ctx *gin.Context) {
	var req listProductsRequest

	if err := ctx.ShouldBindQuery(&req); err != nil {
		validationError(ctx, err)
		return
	}

	products, err := productHandler.listUseCase.ListProducts(ctx, req.Page, req.Limit)
	if err != nil {
		handleError(ctx, err)
		return
	}

	responses := make([]productResponse, 0, len(products))
	for _, product := range products {
		productCopy := product
		responses = append(responses, newProductResponse(&productCopy))
	}

	handleSuccess(ctx, gin.H{
		"products": responses,
		"pagination": gin.H{
			"page":  req.Page,
			"limit": req.Limit,
		},
	})
}
