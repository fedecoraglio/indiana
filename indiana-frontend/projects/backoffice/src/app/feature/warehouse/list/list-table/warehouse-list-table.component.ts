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
import {
  BehaviorSubject,
  debounceTime,
  distinctUntilChanged,
  filter,
  Subject,
  takeUntil,
  tap,
} from 'rxjs';
import { AsyncPipe, CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { WarehouseListDto } from '../../dto/warehouse-list.dto';
import { WarehouseFilterDto } from '../../dto/warehouse-filter.dto';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import {MatIconButton} from "@angular/material/button";

@Component({
  selector: 'indiana-backoffice-warehouse-list-table',
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
    AsyncPipe,
    MatIconButton,
  ],
  templateUrl: './warehouse-list-table.component.html',
  styleUrls: [],
})
export class WarehouseListTableComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  private readonly destroyed$: Subject<void> = new Subject<void>();
  readonly searchInputControl: FormControl = new FormControl();
  readonly itemList$ = new BehaviorSubject<WarehouseListDto | null>(null);
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @Input() set listItem(listDto: WarehouseListDto) {
    this.itemList$.next(listDto);
  }

  @Output() filterEvent = new EventEmitter<WarehouseFilterDto>();
  @Output() sortSelected = new EventEmitter<WarehouseFilterDto>();
  @Output() editEvent = new EventEmitter<string>();
  @Output() viewEvent = new EventEmitter<string>();
  readonly displayedColumns: string[] = ['id', 'name', 'location', 'actions'];
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
          this.sendFilter(query);
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
          this.sendFilter(this.searchInputControl.getRawValue()),
        ),
        takeUntil(this.destroyed$),
      )
      .subscribe();
    this.sort.sortChange
      .pipe(
        tap(() =>
          this.sendFilter(this.searchInputControl.getRawValue()),
        ),
        takeUntil(this.destroyed$),
      )
      .subscribe();
  }

  edit(id: string) {
    this.editEvent.next(id);
  }

  view(id: string) {
    this.viewEvent.next(id);
  }

  private sendFilter(query: string | null) {
    this.filterEvent.next({
      page: this.paginator.pageIndex,
      pageSize: this.paginator.pageSize,
      sortBy: this.sort.active,
      sortOrder: this.sort.direction === '' ? 'desc' : this.sort.direction,
      query: query,
    });
  }
}
