import { Component, inject, OnDestroy } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { WarehouseService } from '../../../core/service/warehouse/warehouse.service';
import { Subject, switchMap, takeUntil } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';

import { WarehouseListTableComponent } from './list-table/warehouse-list-table.component';
import { WarehouseFilterDto } from '../dto/warehouse-filter.dto';
import { MatDialog } from '@angular/material/dialog';
import { WarehouseCreateComponent } from '../create/warehouse-create.component';
import { WarehouseEditComponent } from '../edit/warehouse-edit.component';
import { WarehouseViewComponent } from '../view/warehouse-view.component';

@Component({
  selector: 'indiana-backoffice-warehouse-list',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule,
    WarehouseListTableComponent,
    MatTableModule,
    MatSortModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
  ],
  templateUrl: './warehouse-list.component.html',
  styleUrl: './warehouse-list.component.scss',
})
export class WarehouseListComponent implements OnDestroy {
  private readonly destroyed$: Subject<void> = new Subject<void>();
  private readonly locationService = inject(WarehouseService);
  private readonly showList$ = new Subject<WarehouseFilterDto | null>();
  private readonly defaultFilter: WarehouseFilterDto = {
    page: 0,
    pageSize: 30,
    sortBy: 'id',
    sortOrder: 'desc',
  };
  private defaultModalOption = {
    height: '370px',
    width: '400px',
    useValue: {hasBackdrop: false}
  };
  readonly listSignal = this.locationService.locationListSignal;

  constructor(public dialog: MatDialog) {
    this.showList$
      .pipe(
        switchMap((dto) => this.locationService.getAll$(dto)),
        takeUntilDestroyed(),
      )
      .subscribe();
    this.showList$.next(this.defaultFilter);
  }

  ngOnDestroy() {
    this.destroyed$.next();
  }

  onSelected($event: unknown) {
    this.showList$.next(
      ($event as WarehouseFilterDto | null) ?? this.defaultFilter,
    );
  }

  showCreateModal() {
    this.dialog
      .open(WarehouseCreateComponent, {
        ...this.defaultModalOption,
      })
      .afterClosed()
      .pipe(takeUntil(this.destroyed$))
      .subscribe((data) => {
        this.shouldReloadData(data);
      });
  }

  showEditModal(id: string) {
    this.dialog
      .open(WarehouseEditComponent, {
        ...this.defaultModalOption,
        data: { id },
      })
      .afterClosed()
      .pipe(takeUntil(this.destroyed$))
      .subscribe((data) => {
        this.shouldReloadData(data);
      });
  }

  showViewModal(id: string) {
    this.dialog
    .open(WarehouseViewComponent, {
      ...this.defaultModalOption,
      data: { id },
    })
    .afterClosed()
    .pipe(takeUntil(this.destroyed$))
    .subscribe((data) => {
      this.shouldReloadData(data);
    });
  }

  private shouldReloadData(data: { shouldReload: boolean }) {
    if (data && data.shouldReload) {
      this.showList$.next(this.defaultFilter);
    }
  }
}
