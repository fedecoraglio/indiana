import {
  ChangeDetectionStrategy,
  Component,
  Inject,
  inject,
  Signal,
  ViewChild,
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { filter, from, map, Subject, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { ActivatedRoute, Router } from '@angular/router';
import { ProviderFormComponent } from '../form/provider-form.component';
import { ProviderDto } from '../dto/provider.dto';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { ProviderService } from '../../../core/service/provider/provider.service';

@Component({
  selector: 'indiana-backoffice-provider-edit',
  templateUrl: './provider-edit.component.html',
  providers: [],
  standalone: true,
  imports: [
    ProviderFormComponent,
    MatButtonModule,
    MatDialogActions,
    MatDialogTitle,
    MatDialogContent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProviderEditComponent {
  private readonly providerService = inject(ProviderService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.providerService.isLoadingSignal;
  readonly update$ = new Subject<void>();
  readonly title = 'Edit Provider';
  readonly providerSignal: Signal<ProviderDto | null> =
    this.providerService.providerSignal;
  @ViewChild('form') form!: ProviderFormComponent;

  constructor(
    public dialogRef: MatDialogRef<ProviderEditComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      id: string;
    },
  ) {
    this.providerService
      .getById$(data.id)
      .pipe(takeUntilDestroyed())
      .subscribe();

    this.gotToList$.pipe(takeUntilDestroyed()).subscribe(() => {
      this.dialogRef.close({ shouldReload: true });
    });

    this.update$
      .pipe(
        filter(() => this.form.isValid),
        switchMap(() => this.providerService.update$(data.id, this.form.value)),
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
