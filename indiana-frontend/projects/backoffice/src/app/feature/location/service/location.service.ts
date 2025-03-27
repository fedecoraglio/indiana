import { computed, inject, Injectable, signal } from '@angular/core';
import { catchError, EMPTY, finalize, Observable, take, tap } from 'rxjs';
import { LocationListDto } from '../dto/location-list.dto';
import { LocationApiService } from './location-api.service';
import { LocationFilterDto } from '../dto/location-filter.dto';
import { LocationDto } from '../dto/location.dto';
import { SnackBarService } from '../../../pattern/snack-bar/snack-bar.service';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class LocationService {
  private readonly locationApiService = inject(LocationApiService);
  private readonly snackBarService = inject(SnackBarService);
  private readonly emptyLocationListDto: LocationListDto = {
    totalElements: 0,
    totalPages: 0,
    pageSize: 0,
    page: 0,
    numberOfElements: 0,
    content: [],
  };
  // Writable signal
  private readonly _locationListSignal = signal<LocationListDto>(
    this.emptyLocationListDto,
  );
  private readonly _locationSignal = signal<LocationDto | null>(null);
  private readonly _isLoadingSignal = signal<boolean>(false);
  // Immutable signal
  readonly locationListSignal = computed(() => this._locationListSignal());
  readonly locationSignal = computed(() => this._locationSignal() ?? null);
  readonly isLoadingSignal = computed(() => this._isLoadingSignal());

  getAll$(dto: LocationFilterDto | null = null): Observable<LocationListDto> {
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

  save$(dto: LocationDto): Observable<LocationDto> {
    this._isLoadingSignal.set(true);
    return this.locationApiService.save$(dto).pipe(
      take(1),
      tap((itemDto: LocationDto) => {
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

  update$(id: string, dto: LocationDto): Observable<LocationDto> {
    this._isLoadingSignal.set(true);
    return this.locationApiService.update$(id, dto).pipe(
        take(1),
        tap((dtoItem: LocationDto) => {
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

  getById$(id: string): Observable<LocationDto> {
    return this.locationApiService.getById$(id).pipe(
      take(1),
      tap((itemDto: LocationDto) => {
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
