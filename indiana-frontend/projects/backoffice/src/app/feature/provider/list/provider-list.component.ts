import { Component, inject, OnDestroy } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Subject, switchMap, takeUntil } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';

import { ProviderListTableComponent } from './list-table/provider-list-table.component';
import { ProviderFilterDto } from '../dto/provider-filter.dto';
import { MatDialog } from '@angular/material/dialog';
import { ProviderCreateComponent } from '../create/provider-create.component';
import { ProviderEditComponent } from '../edit/provider-edit.component';
import { ProviderViewComponent } from '../view/provider-view.component';
import { ProviderService } from '../../../core/service/provider/provider.service';

@Component({
  selector: 'indiana-backoffice-provider-list',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule,
    ProviderListTableComponent,
    MatTableModule,
    MatSortModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
  ],
  templateUrl: './provider-list.component.html',
  styleUrl: './provider-list.component.scss',
})
export class ProviderListComponent implements OnDestroy {
  private readonly destroyed$: Subject<void> = new Subject<void>();
  private readonly providerService = inject(ProviderService);
  private readonly showList$ = new Subject<ProviderFilterDto | null>();
  private readonly defaultFilter: ProviderFilterDto = {
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
  readonly listSignal = this.providerService.providerListSignal;

  constructor(public dialog: MatDialog) {
    this.showList$
      .pipe(
        switchMap((dto) => this.providerService.getAll$(dto)),
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
      ($event as ProviderFilterDto | null) ?? this.defaultFilter,
    );
  }

  showCreateModal() {
    this.dialog
      .open(ProviderCreateComponent, {
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
      .open(ProviderEditComponent, {
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
    .open(ProviderViewComponent, {
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
