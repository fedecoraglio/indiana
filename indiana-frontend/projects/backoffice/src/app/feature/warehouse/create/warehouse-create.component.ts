import {
  ChangeDetectionStrategy,
  Component,
  inject,
  ViewChild,
} from '@angular/core';
import { WarehouseService } from '../../../core/service/warehouse/warehouse.service';
import { Router } from '@angular/router';
import { filter, Subject, switchMap } from 'rxjs';
import { WarehouseFormComponent } from '../form/warehouse-form.component';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { WarehouseApiService } from '../../../core/service/warehouse/warehouse-api.service';

@Component({
  selector: 'indiana-backoffice-warehouse-create',
  templateUrl: './warehouse-create.component.html',
  providers: [WarehouseService, WarehouseApiService],
  standalone: true,
  imports: [
    WarehouseFormComponent,
    MatButtonModule,
    MatDialogTitle,
    MatDialogActions,
    MatDialogContent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WarehouseCreateComponent {
  private readonly locationService = inject(WarehouseService);
  private readonly router = inject(Router);
  readonly create$ = new Subject<void>();
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.locationService.isLoadingSignal;
  @ViewChild('form') form!: WarehouseFormComponent;

  constructor(public dialogRef: MatDialogRef<WarehouseCreateComponent>) {
    this.gotToList$.pipe(takeUntilDestroyed()).subscribe(() => {
      this.dialogRef.close({ shouldReload: true });
    });

    this.create$
      .pipe(
        filter(() => this.form.isValid),
        switchMap(() => this.locationService.save$(this.form.value)),
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
