import { computed, inject, Injectable, signal } from '@angular/core';
import { catchError, EMPTY, finalize, Observable, take, tap } from 'rxjs';
import { ProductListDto } from '../dto/product-list.dto';
import { ProductApiService } from './product-api.service';
import { ProductFilterDto } from '../dto/product-filter.dto';
import { ProductDto } from '../dto/product.dto';
import { SnackBarService } from '../../../pattern/snack-bar/snack-bar.service';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class ProductService {
  private readonly productApiService = inject(ProductApiService);
  private readonly snackBarService = inject(SnackBarService);
  private readonly emptyProductListDto: ProductListDto = {
    totalElements: 0,
    totalPages: 0,
    pageSize: 0,
    page: 0,
    numberOfElements: 0,
    content: [],
  };
  // Writable signal
  private readonly _productListSignal = signal<ProductListDto>(
    this.emptyProductListDto,
  );
  private readonly _productSignal = signal<ProductDto | null>(null);
  private readonly _isLoadingSignal = signal<boolean>(false);
  // Immutable signal
  readonly productListSignal = computed(() => this._productListSignal());
  readonly productSignal = computed(() => this._productSignal() ?? null);
  readonly isLoadingSignal = computed(() => this._isLoadingSignal());

  getAll$(dto: ProductFilterDto | null = null): Observable<ProductListDto> {
    return this.productApiService.getAll$(dto).pipe(
      take(1),
      tap((productListDto) => {
        this._productListSignal.set(productListDto);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error getting products',
        );
        return EMPTY;
      }),
    );
  }

  save$(dto: ProductDto): Observable<ProductDto> {
    this._isLoadingSignal.set(true);
    return this.productApiService.save$(dto).pipe(
      take(1),
      tap((productDto: ProductDto) => {
        this._productSignal.set(productDto);
        this.snackBarService.showMessage(`${dto.name} has been saved`);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error saving product',
        );
        return EMPTY;
      }),
      finalize(() => this._isLoadingSignal.set(false)),
    );
  }

  update$(id: string, dto: ProductDto): Observable<ProductDto> {
    this._isLoadingSignal.set(true);
    return this.productApiService.update$(id, dto).pipe(
        take(1),
        tap((productDto: ProductDto) => {
          this._productSignal.set(productDto);
          this.snackBarService.showMessage(`${dto.name} has been updated`);
        }),
        catchError((e: HttpErrorResponse) => {
          this.snackBarService.showError(
              e.error?.message ?? 'Error updating product',
          );
          return EMPTY;
        }),
        finalize(() => this._isLoadingSignal.set(false)),
    );
  }

  getById$(id: string): Observable<ProductDto> {
    return this.productApiService.getById$(id).pipe(
      take(1),
      tap((productDto: ProductDto) => {
        this._productSignal.set(productDto);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error getting product',
        );
        return EMPTY;
      }),
    );
  }
}
