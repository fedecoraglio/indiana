import { Component, inject, OnDestroy } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { LocationService } from '../service/location.service';
import { Subject, switchMap, takeUntil } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AsyncPipe } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';

import { LocationListTableComponent } from './list-table/location-list-table.component';
import { LocationFilterDto } from '../dto/location-filter.dto';
import { MatButtonToggle } from '@angular/material/button-toggle';
import { MatDialog } from '@angular/material/dialog';
import { LocationCreateComponent } from '../create/location-create.component';
import { LocationEditComponent } from '../edit/location-edit.component';
import { LocationViewComponent } from '../view/location-view.component';

@Component({
  selector: 'indiana-backoffice-location-list',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule,
    LocationListTableComponent,
    MatTableModule,
    MatSortModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
  ],
  templateUrl: './location-list.component.html',
  styleUrl: './location-list.component.scss',
})
export class LocationListComponent implements OnDestroy {
  private readonly destroyed$: Subject<void> = new Subject<void>();
  private readonly locationService = inject(LocationService);
  private readonly showList$ = new Subject<LocationFilterDto | null>();
  private readonly defaultFilter: LocationFilterDto = {
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
      ($event as LocationFilterDto | null) ?? this.defaultFilter,
    );
  }

  showCreateModal() {
    this.dialog
      .open(LocationCreateComponent, {
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
      .open(LocationEditComponent, {
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
    .open(LocationViewComponent, {
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
