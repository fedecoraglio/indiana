package domain

import (
	"time"
)

type Product struct {
	ID           int64
	Code         string
	BarCode      string
	Name         string
	Description  string
	PackedWeight float64
	PackedWidth  float64
	PackedDepth  float64
	CreatedAt    time.Time
	UpdatedAt    time.Time
}
