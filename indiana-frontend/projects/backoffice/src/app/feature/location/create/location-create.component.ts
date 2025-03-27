import {
  ChangeDetectionStrategy,
  Component,
  inject,
  ViewChild,
} from '@angular/core';
import { LocationService } from '../service/location.service';
import { Router } from '@angular/router';
import { filter, from, Subject, switchMap, tap } from 'rxjs';
import { LocationFormComponent } from '../form/location-form.component';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { LocationApiService } from '../service/location-api.service';

@Component({
  selector: 'indiana-backoffice-location-create',
  templateUrl: './location-create.component.html',
  providers: [LocationService, LocationApiService],
  standalone: true,
  imports: [
    LocationFormComponent,
    MatButtonModule,
    MatDialogTitle,
    MatDialogActions,
    MatDialogContent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LocationCreateComponent {
  private readonly locationService = inject(LocationService);
  private readonly router = inject(Router);
  readonly create$ = new Subject<void>();
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.locationService.isLoadingSignal;
  @ViewChild('form') form!: LocationFormComponent;

  constructor(public dialogRef: MatDialogRef<LocationCreateComponent>) {
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
