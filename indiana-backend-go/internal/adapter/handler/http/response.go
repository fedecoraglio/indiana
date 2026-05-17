package http

import (
	"errors"
	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"indiana-backend-go/internal/core/domain"
	"net/http"
	"time"
)

// response represents a response body format
type response struct {
	Success bool   `json:"success" example:"true"`
	Message string `json:"message" example:"Success"`
	Data    any    `json:"data,omitempty"`
}

// newResponse is a helper function to create a response body
func newResponse(success bool, message string, data any) response {
	return response{
		Success: success,
		Message: message,
		Data:    data,
	}
}

// productResponse represents a product response body
type productResponse struct {
	ID           int64     `json:"id" example:"1231"`
	Code         string    `json:"code" example:"123-abx"`
	BarCode      string    `json:"barCode" example:"any string"`
	Name         string    `json:"name" example:"Notebook"`
	Description  string    `json:"description" example:"new notebook"`
	PackedWeight float64   `json:"packedWeight" example:"0"`
	PackedWidth  float64   `json:"packedWidth" example:"0"`
	PackedDepth  float64   `json:"packedDepth" example:"0"`
	CreatedAt    time.Time `json:"createdAt" example:"2026-04-06T21:00:00Z"`
	UpdatedAt    time.Time `json:"updatedAt" example:"2026-04-06T21:00:00Z"`
}

func newProductResponse(product *domain.Product) productResponse {
	return productResponse{
		ID:           product.ID,
		Code:         product.Code,
		BarCode:      product.BarCode,
		Name:         product.Name,
		Description:  product.Description,
		PackedWeight: product.PackedWeight,
		PackedWidth:  product.PackedWeight,
		PackedDepth:  product.PackedWeight,
		CreatedAt:    product.CreatedAt,
		UpdatedAt:    product.UpdatedAt,
	}
}

// errorStatusMap is a map of defined error messages and their corresponding http status codes
var errorStatusMap = map[error]int{
	domain.ErrInternal:        http.StatusInternalServerError,
	domain.ErrDataNotFound:    http.StatusNotFound,
	domain.ErrConflictingData: http.StatusConflict,
	domain.ErrNoUpdatedData:   http.StatusBadRequest,
}

// validationError sends an error response for some specific request validation error
func validationError(ctx *gin.Context, err error) {
	errMsgs := parseError(err)
	errRsp := newErrorResponse(errMsgs)
	ctx.JSON(http.StatusBadRequest, errRsp)
}

// handleError determines the status code of an error and returns a JSON response with the error message and status code
func handleError(ctx *gin.Context, err error) {
	statusCode, ok := errorStatusMap[err]
	if !ok {
		statusCode = http.StatusInternalServerError
	}

	errMsg := parseError(err)
	errRsp := newErrorResponse(errMsg)
	ctx.JSON(statusCode, errRsp)
}

// parseError parses error messages from the error object and returns a slice of error messages
func parseError(err error) []string {
	var errMsgs []string

	if errors.As(err, &validator.ValidationErrors{}) {
		for _, err := range err.(validator.ValidationErrors) {
			errMsgs = append(errMsgs, err.Error())
		}
	} else {
		errMsgs = append(errMsgs, err.Error())
	}

	return errMsgs
}

// errorResponse represents an error response body format
type errorResponse struct {
	Success  bool     `json:"success" example:"false"`
	Messages []string `json:"messages" example:"Error message 1, Error message 2"`
}

// newErrorResponse is a helper function to create an error response body
func newErrorResponse(errMsgs []string) errorResponse {
	return errorResponse{
		Success:  false,
		Messages: errMsgs,
	}
}

// handleSuccess sends a success response with the specified status code and optional data
func handleSuccess(ctx *gin.Context, data any) {
	rsp := newResponse(true, "Success", data)
	ctx.JSON(http.StatusOK, rsp)
}
