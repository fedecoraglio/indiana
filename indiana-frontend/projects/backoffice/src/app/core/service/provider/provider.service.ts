import { computed, inject, Injectable, signal } from '@angular/core';
import { catchError, EMPTY, finalize, Observable, take, tap } from 'rxjs';
import { ProviderApiService } from './provider-api.service';
import { SnackBarService } from '../../../pattern/snack-bar/snack-bar.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProviderListDto } from "../../../feature/provider/dto/provider-list.dto";
import { ProviderDto } from "../../../feature/provider/dto/provider.dto";
import { ProviderFilterDto } from "../../../feature/provider/dto/provider-filter.dto";

@Injectable({providedIn: 'root'})
export class ProviderService {
  private readonly apiService = inject(ProviderApiService);
  private readonly snackBarService = inject(SnackBarService);
  private readonly emptyListDto: ProviderListDto = {
    totalElements: 0,
    totalPages: 0,
    pageSize: 0,
    page: 0,
    numberOfElements: 0,
    content: [],
  };
  // Writable signal
  private readonly _listSignal = signal<ProviderListDto>(
    this.emptyListDto,
  );
  private readonly _providerSignal = signal<ProviderDto | null>(null);
  private readonly _isLoadingSignal = signal<boolean>(false);
  // Immutable signal
  readonly providerListSignal = computed(() => this._listSignal());
  readonly providerSignal = computed(() => this._providerSignal() ?? null);
  readonly isLoadingSignal = computed(() => this._isLoadingSignal());

  getAll$(dto: ProviderFilterDto | null = null): Observable<ProviderListDto> {
    return this.apiService.getAll$(dto).pipe(
      take(1),
      tap((dtoList) => {
        this._listSignal.set(dtoList);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error getting providers',
        );
        return EMPTY;
      }),
    );
  }

  save$(dto: ProviderDto): Observable<ProviderDto> {
    this._isLoadingSignal.set(true);
    return this.apiService.save$(dto).pipe(
      take(1),
      tap((itemDto: ProviderDto) => {
        this._providerSignal.set(itemDto);
        this.snackBarService.showMessage(`${dto.name} has been saved`);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(e.error?.message ?? 'Error saving provider');
        return EMPTY;
      }),
      finalize(() => this._isLoadingSignal.set(false)),
    );
  }

  update$(id: string, dto: ProviderDto): Observable<ProviderDto> {
    this._isLoadingSignal.set(true);
    return this.apiService.update$(id, dto).pipe(
        take(1),
        tap((dtoItem: ProviderDto) => {
          this._providerSignal.set(dtoItem);
          this.snackBarService.showMessage(`${dto.name} has been updated`);
        }),
        catchError((e: HttpErrorResponse) => {
          this.snackBarService.showError(
              e.error?.message ?? 'Error updating provider',
          );
          return EMPTY;
        }),
        finalize(() => this._isLoadingSignal.set(false)),
    );
  }

  getById$(id: string): Observable<ProviderDto> {
    return this.apiService.getById$(id).pipe(
      take(1),
      tap((itemDto: ProviderDto) => {
        this._providerSignal.set(itemDto);
      }),
      catchError((e: HttpErrorResponse) => {
        this.snackBarService.showError(
          e.error?.message ?? 'Error getting provider',
        );
        return EMPTY;
      }),
    );
  }
}
