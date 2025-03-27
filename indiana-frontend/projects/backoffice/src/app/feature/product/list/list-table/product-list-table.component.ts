import {
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { AsyncPipe, CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { ProductListDto } from '../../dto/product-list.dto';
import { ProductFilterDto } from '../../dto/product-filter.dto';
import {
  BehaviorSubject,
  debounceTime,
  distinctUntilChanged,
  filter,
  Subject,
  switchMap,
  takeUntil,
  tap,
} from 'rxjs';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import {MatIconButton} from "@angular/material/button";

@Component({
  selector: 'indiana-backoffice-product-list-table',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSortModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatIconModule,
    RouterLink,
    AsyncPipe,
    MatIconButton,
  ],
  templateUrl: './product-list-table.component.html',
  styleUrls: [],
})
export class ProductListTableComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  private readonly destroyed$: Subject<void> = new Subject<void>();
  readonly searchInputControl: FormControl = new FormControl();
  readonly productList$ = new BehaviorSubject<ProductListDto | null>(null);
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @Input() set productList(productListDto: ProductListDto) {
    this.productList$.next(productListDto);
  }

  @Output() productFilter = new EventEmitter<ProductFilterDto>();
  @Output() sortSelected = new EventEmitter<ProductFilterDto>();
  readonly displayedColumns: string[] = [
    'id',
    'name',
    'code',
    'barcode',
    'actions',
  ];
  readonly pageSizeOptions: number[] = [30, 60, 100];
  readonly defaultPageSize: number = 30;

  ngOnInit() {
    this.searchInputControl.valueChanges
      .pipe(
        filter<string>((query) => query.length > 0 || query.length === 0),
        debounceTime(200),
        distinctUntilChanged(),
        tap((query: string) => {
          this.paginator.pageIndex = 0;
          this.sendProductFilter(query);
        }),
        takeUntil(this.destroyed$),
      )
      .subscribe();
  }

  ngOnDestroy() {
    this.destroyed$.next();
  }

  ngAfterViewInit() {
    this.paginator.page
      .pipe(
        tap(() =>
          this.sendProductFilter(this.searchInputControl.getRawValue()),
        ),
        takeUntil(this.destroyed$),
      )
      .subscribe();
    this.sort.sortChange
      .pipe(
        tap(() =>
          this.sendProductFilter(this.searchInputControl.getRawValue()),
        ),
        takeUntil(this.destroyed$),
      )
      .subscribe();
  }

  private sendProductFilter(query: string | null) {
    this.productFilter.next({
      page: this.paginator.pageIndex,
      pageSize: this.paginator.pageSize,
      sortBy: this.sort.active,
      sortOrder: this.sort.direction === '' ? 'desc' : this.sort.direction,
      query: query,
    });
  }
}
