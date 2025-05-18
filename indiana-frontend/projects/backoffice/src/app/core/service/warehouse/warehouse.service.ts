import { computed, inject, Injectable, signal } from '@angular/core';
import { catchError, EMPTY, finalize, Observable, take, tap } from 'rxjs';
import { WarehouseListDto } from '../../../feature/warehouse/dto/warehouse-list.dto';
import { WarehouseApiService } from './warehouse-api.service';
import { WarehouseFilterDto } from '../../../feature/warehouse/dto/warehouse-filter.dto';
import { WarehouseDto } from '../../../feature/warehouse/dto/warehouse.dto';
import { SnackBarService } from '../../../pattern/snack-bar/snack-bar.service';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class WarehouseService {
  private readonly locationApiService = inject(WarehouseApiService);
  private readonly snackBarService = inject(SnackBarService);
  private readonly emptyLocationListDto: WarehouseListDto = {
    totalElements: 0,
    totalPages: 0,
    pageSize: 0,
    page: 0,
    numberOfElements: 0,
    content: [],
  };
  // Writable signal
  private readonly _locationListSignal = signal<WarehouseListDto>(
    this.emptyLocationListDto,
  );
  private readonly _locationSignal = signal<WarehouseDto | null>(null);
  private readonly _isLoadingSignal = signal<boolean>(false);
  // Immutable signal
  readonly locationListSignal = computed(() => this._locationListSignal());
  readonly locationSignal = computed(() => this._locationSignal() ?? null);
  readonly isLoadingSignal = computed(() => this._isLoadingSignal());

  getAll$(dto: WarehouseFilterDto | null = null): Observable<WarehouseListDto> {
    return this.locationApiService.getAll$(dto).pipe(
      take(1),
      tap((dtoList) => {
        this._locationListSignal.set(dtoList);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error getting locations',
        );
        return EMPTY;
      }),
    );
  }

  save$(dto: WarehouseDto): Observable<WarehouseDto> {
    this._isLoadingSignal.set(true);
    return this.locationApiService.save$(dto).pipe(
      take(1),
      tap((itemDto: WarehouseDto) => {
        this._locationSignal.set(itemDto);
        this.snackBarService.showMessage(`${dto.name} has been saved`);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error saving location',
        );
        return EMPTY;
      }),
      finalize(() => this._isLoadingSignal.set(false)),
    );
  }

  update$(id: string, dto: WarehouseDto): Observable<WarehouseDto> {
    this._isLoadingSignal.set(true);
    return this.locationApiService.update$(id, dto).pipe(
        take(1),
        tap((dtoItem: WarehouseDto) => {
          this._locationSignal.set(dtoItem);
          this.snackBarService.showMessage(`${dto.name} has been updated`);
        }),
        catchError((e: HttpErrorResponse) => {
          this.snackBarService.showError(
              e.error?.message ?? 'Error updating location',
          );
          return EMPTY;
        }),
        finalize(() => this._isLoadingSignal.set(false)),
    );
  }

  getById$(id: string): Observable<WarehouseDto> {
    return this.locationApiService.getById$(id).pipe(
      take(1),
      tap((itemDto: WarehouseDto) => {
        this._locationSignal.set(itemDto);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error getting location',
        );
        return EMPTY;
      }),
    );
  }
}
