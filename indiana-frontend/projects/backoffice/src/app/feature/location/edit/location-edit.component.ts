import {
  ChangeDetectionStrategy,
  Component,
  Inject,
  inject,
  Signal,
  ViewChild,
} from '@angular/core';
import { LocationService } from '../service/location.service';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, from, map, Subject, switchMap } from 'rxjs';
import { LocationFormComponent } from '../form/location-form.component';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { LocationDto } from '../dto/location.dto';
import { LocationApiService } from '../service/location-api.service';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';

@Component({
  selector: 'indiana-backoffice-location-edit',
  templateUrl: './location-edit.component.html',
  providers: [LocationService, LocationApiService],
  standalone: true,
  imports: [
    LocationFormComponent,
    MatButtonModule,
    MatDialogActions,
    MatDialogTitle,
    MatDialogContent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LocationEditComponent {
  private readonly locationService = inject(LocationService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.locationService.isLoadingSignal;
  readonly update$ = new Subject<void>();
  readonly title = 'Edit Location';
  readonly locationSignal: Signal<LocationDto | null> =
    this.locationService.locationSignal;
  @ViewChild('form') form!: LocationFormComponent;

  constructor(
    public dialogRef: MatDialogRef<LocationEditComponent>,
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

    this.update$
      .pipe(
        filter(() => this.form.isValid),
        switchMap(() => this.locationService.update$(data.id, this.form.value)),
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
