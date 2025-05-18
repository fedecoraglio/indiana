import {
  ChangeDetectionStrategy,
  Component,
  Inject,
  inject,
  Signal,
  ViewChild,
} from '@angular/core';
import { WarehouseService } from '../../../core/service/warehouse/warehouse.service';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, Subject, switchMap } from 'rxjs';
import { WarehouseFormComponent } from '../form/warehouse-form.component';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { WarehouseDto } from '../dto/warehouse.dto';
import { WarehouseApiService } from '../../../core/service/warehouse/warehouse-api.service';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';

@Component({
  selector: 'indiana-backoffice-warehouse-edit',
  templateUrl: './warehouse-edit.component.html',
  providers: [WarehouseService, WarehouseApiService],
  standalone: true,
  imports: [
    WarehouseFormComponent,
    MatButtonModule,
    MatDialogActions,
    MatDialogTitle,
    MatDialogContent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WarehouseEditComponent {
  private readonly warehouseService = inject(WarehouseService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.warehouseService.isLoadingSignal;
  readonly update$ = new Subject<void>();
  readonly title = 'Edit Location';
  readonly locationSignal: Signal<WarehouseDto | null> = this.warehouseService.locationSignal;
  @ViewChild('form') form!: WarehouseFormComponent;

  constructor(
    public dialogRef: MatDialogRef<WarehouseEditComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      id: string;
    },
  ) {
    this.warehouseService
      .getById$(data.id)
      .pipe(takeUntilDestroyed())
      .subscribe();

    this.gotToList$.pipe(takeUntilDestroyed()).subscribe(() => {
      this.dialogRef.close({ shouldReload: true });
    });

    this.update$
      .pipe(
        filter(() => this.form.isValid),
        switchMap(() => this.warehouseService.update$(data.id, this.form.value)),
        takeUntilDestroyed(),
      )
      .subscribe(() => {
        this.gotToList$.next();
      });
  }

  cancel() {
    this.dialogRef.close({ shouldReload: false });
  }
}
