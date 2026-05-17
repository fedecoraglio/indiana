package repository

import (
	"context"
	"database/sql"
	"errors"
	"fmt"
	"log"

	sq "github.com/Masterminds/squirrel"

	"indiana-backend-go/internal/adapter/store/mysql"
	"indiana-backend-go/internal/core/domain"
)

// ProductRepository provides access to product persistence.
type ProductRepository struct {
	db *mysql.DB
}

// NewProductRepository creates a new product repository instance.
func NewProductRepository(db *mysql.DB) *ProductRepository {
	return &ProductRepository{db: db}
}

// CreateProduct creates a new product record in the database.
func (pr *ProductRepository) CreateProduct(ctx context.Context, product domain.Product) (*domain.Product, error) {
	query := pr.db.QueryBuilder.Insert("products").
		Columns(
			"pro_code",
			"pro_barcode",
			"pro_name",
			"pro_description",
			"pro_packed_weight",
			"pro_packed_width",
			"pro_packed_depth",
		).
		Values(
			product.Code,
			product.BarCode,
			product.Name,
			product.Description,
			product.PackedWeight,
			product.PackedWidth,
			product.PackedDepth,
		)

	sqlQuery, args, err := query.ToSql()
	if err != nil {
		return nil, err
	}

	result, err := pr.db.ExecContext(ctx, sqlQuery, args...)
	if err != nil {
		if errCode := pr.db.ErrorCode(err); errCode == "1062" {
			return nil, domain.ErrConflictingData
		}
		return nil, err
	}

	lastID, err := result.LastInsertId()
	if err != nil {
		return nil, err
	}

	return pr.GetProductByID(ctx, lastID)
}

// GetProductByID retrieves a product by ID.
func (pr *ProductRepository) GetProductByID(ctx context.Context, id int64) (*domain.Product, error) {
	var product domain.Product

	query := pr.db.QueryBuilder.
		Select(
			"prod_id",
			"pro_code",
			"pro_barcode",
			"pro_name",
			"pro_description",
			"pro_packed_weight",
			"pro_packed_width",
			"pro_packed_depth",
		).
		From("products").
		Where(sq.Eq{"prod_id": id}).
		Limit(1)

	sqlQuery, args, err := query.ToSql()
	if err != nil {
		return nil, err
	}

	err = pr.db.QueryRowContext(ctx, sqlQuery, args...).Scan(
		&product.ID,
		&product.Code,
		&product.BarCode,
		&product.Name,
		&product.Description,
		&product.PackedWeight,
		&product.PackedWidth,
		&product.PackedDepth,
	)

	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			return nil, domain.ErrDataNotFound
		}
		return nil, err
	}

	return &product, nil
}

// GetProductByCodeOrBarCodeOrName retrieves products by code, barcode or name.
// GetProductByCodeOrBarCodeOrName retrieves products by code, barcode or name.
func (pr *ProductRepository) GetProductByCodeOrBarCodeOrName(
	ctx context.Context,
	code string,
	barCode string,
	name string,
) ([]domain.Product, error) {
	var products []domain.Product

	query := pr.db.QueryBuilder.
		Select(
			"prod_id",
			"pro_code",
			"pro_barcode",
			"pro_name",
			"pro_description",
			"pro_packed_weight",
			"pro_packed_width",
			"pro_packed_depth",
		).
		From("products").
		OrderBy("prod_id")

	var conditions sq.Or

	if code != "" {
		conditions = append(
			conditions,
			sq.Expr("LOWER(pro_code) = LOWER(?)", code),
		)
	}

	if barCode != "" {
		conditions = append(
			conditions,
			sq.Expr("LOWER(pro_barcode) = LOWER(?)", barCode),
		)
	}

	if name != "" {
		conditions = append(
			conditions,
			sq.Expr("LOWER(pro_name) = LOWER(?)", name),
		)
	}

	if len(conditions) > 0 {
		query = query.Where(conditions)
	}

	sqlQuery, args, err := query.ToSql()
	if err != nil {
		return nil, err
	}

	rows, err := pr.db.QueryContext(ctx, sqlQuery, args...)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	for rows.Next() {
		var product domain.Product

		err := rows.Scan(
			&product.ID,
			&product.Code,
			&product.BarCode,
			&product.Name,
			&product.Description,
			&product.PackedWeight,
			&product.PackedWidth,
			&product.PackedDepth,
		)
		if err != nil {
			return nil, err
		}

		products = append(products, product)
	}

	if err := rows.Err(); err != nil {
		return nil, err
	}

	return products, nil
}

// PathProduct updates a product record in the database.
func (pr *ProductRepository) PathProduct(ctx context.Context, product domain.Product) (*domain.Product, error) {
	query := pr.db.QueryBuilder.
		Update("products").
		Where(sq.Eq{"prod_id": product.ID})

	hasChanges := false

	if product.Code != "" {
		query = query.Set("pro_code", product.Code)
		hasChanges = true
	}

	if product.BarCode != "" {
		query = query.Set("pro_barcode", product.BarCode)
		hasChanges = true
	}

	if product.Name != "" {
		query = query.Set("pro_name", product.Name)
		hasChanges = true
	}

	if product.Description != "" {
		query = query.Set("pro_description", product.Description)
		hasChanges = true
	}

	if product.PackedWeight != 0 {
		query = query.Set("pro_packed_weight", product.PackedWeight)
		hasChanges = true
	}

	if product.PackedWidth != 0 {
		query = query.Set("pro_packed_width", product.PackedWidth)
		hasChanges = true
	}

	if product.PackedDepth != 0 {
		query = query.Set("pro_packed_depth", product.PackedDepth)
		hasChanges = true
	}

	if !hasChanges {
		return pr.GetProductByID(ctx, product.ID)
	}

	sqlQuery, args, err := query.ToSql()
	log.Println("pathProduct", sqlQuery, args)
	fmt.Println("pathProduct", sqlQuery, args)
	if err != nil {
		return nil, err
	}

	result, err := pr.db.ExecContext(ctx, sqlQuery, args...)
	if err != nil {
		if errCode := pr.db.ErrorCode(err); errCode == "1062" {
			return nil, domain.ErrConflictingData
		}
		return nil, err
	}

	rowsAffected, err := result.RowsAffected()
	if err != nil {
		return nil, err
	}

	if rowsAffected == 0 {
		return nil, domain.ErrNoUpdatedData
	}

	return pr.GetProductByID(ctx, product.ID)
}

// DeleteProduct deletes a product record from the database by ID.
func (pr *ProductRepository) DeleteProduct(ctx context.Context, id int64) error {
	query := pr.db.QueryBuilder.
		Delete("products").
		Where(sq.Eq{"prod_id": id})

	sqlQuery, args, err := query.ToSql()
	if err != nil {
		return err
	}

	result, err := pr.db.ExecContext(ctx, sqlQuery, args...)
	if err != nil {
		return err
	}

	rowsAffected, err := result.RowsAffected()
	if err != nil {
		return err
	}

	if rowsAffected == 0 {
		return domain.ErrDataNotFound
	}

	return nil
}

func (pr *ProductRepository) ListProducts(ctx context.Context, page uint64, limit uint64) ([]domain.Product, error) {
	var products []domain.Product

	if page == 0 {
		page = 1
	}

	if limit == 0 {
		limit = 10
	}

	offset := (page - 1) * limit

	query := pr.db.QueryBuilder.
		Select(
			"prod_id",
			"pro_code",
			"pro_barcode",
			"pro_name",
			"pro_description",
			"pro_packed_weight",
			"pro_packed_width",
			"pro_packed_depth",
		).
		From("products").
		OrderBy("prod_id").
		Limit(limit).
		Offset(offset)

	sqlQuery, args, err := query.ToSql()
	if err != nil {
		return nil, err
	}

	rows, err := pr.db.QueryContext(ctx, sqlQuery, args...)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	for rows.Next() {
		var product domain.Product

		err := rows.Scan(
			&product.ID,
			&product.Code,
			&product.BarCode,
			&product.Name,
			&product.Description,
			&product.PackedWeight,
			&product.PackedWidth,
			&product.PackedDepth,
		)
		if err != nil {
			return nil, err
		}

		products = append(products, product)
	}

	if err := rows.Err(); err != nil {
		return nil, err
	}

	return products, nil
}
