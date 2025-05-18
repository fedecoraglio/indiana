import {
  ChangeDetectionStrategy,
  Component,
  Inject,
  inject,
  ViewChild,
} from '@angular/core';
import { WarehouseService } from '../../../core/service/warehouse/warehouse.service';
import { Subject } from 'rxjs';
import { WarehouseFormComponent } from '../form/warehouse-form.component';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { WarehouseApiService } from '../../../core/service/warehouse/warehouse-api.service';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent, MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';

@Component({
  selector: 'indiana-backoffice-warehouse-edit',
  templateUrl: './warehouse-view.component.html',
  providers: [WarehouseService, WarehouseApiService],
  standalone: true,
  imports: [
    WarehouseFormComponent,
    MatButtonModule,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WarehouseViewComponent {
  private readonly locationService = inject(WarehouseService);
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.locationService.isLoadingSignal;
  readonly locationSignal = this.locationService.locationSignal;
  @ViewChild('form') form!: WarehouseFormComponent;

  constructor(
    public dialogRef: MatDialogRef<WarehouseViewComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      id: string;
    },
  ) {
    this.locationService
      .getById$(data.id)
      .pipe(takeUntilDestroyed())
      .subscribe();

    this.gotToList$.pipe(takeUntilDestroyed()).subscribe(() => {
      this.dialogRef.close({ shouldReload: true });
    });
  }

  cancel() {
    this.dialogRef.close({ shouldReload: false });
  }
}
