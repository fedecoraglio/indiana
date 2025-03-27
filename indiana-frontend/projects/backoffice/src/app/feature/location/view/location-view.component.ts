import {
  ChangeDetectionStrategy,
  Component,
  Inject,
  inject,
  ViewChild,
} from '@angular/core';
import { LocationService } from '../service/location.service';
import { Subject } from 'rxjs';
import { LocationFormComponent } from '../form/location-form.component';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { LocationApiService } from '../service/location-api.service';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent, MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';

@Component({
  selector: 'indiana-backoffice-location-edit',
  templateUrl: './location-view.component.html',
  providers: [LocationService, LocationApiService],
  standalone: true,
  imports: [
    LocationFormComponent,
    MatButtonModule,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LocationViewComponent {
  private readonly locationService = inject(LocationService);
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.locationService.isLoadingSignal;
  readonly locationSignal = this.locationService.locationSignal;
  @ViewChild('form') form!: LocationFormComponent;

  constructor(
    public dialogRef: MatDialogRef<LocationViewComponent>,
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
