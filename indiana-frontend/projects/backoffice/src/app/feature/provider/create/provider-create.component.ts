import {
  ChangeDetectionStrategy,
  Component,
  inject,
  ViewChild,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { filter, Subject, switchMap } from 'rxjs';
import { Router } from '@angular/router';
import {
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

import { ProviderFormComponent } from '../form/provider-form.component';
import { ProviderService } from '../../../core/service/provider/provider.service';

@Component({
  selector: 'indiana-backoffice-provider-create',
  templateUrl: './provider-create.component.html',
  providers: [],
  standalone: true,
  imports: [
    ProviderFormComponent,
    MatButtonModule,
    MatDialogTitle,
    MatDialogActions,
    MatDialogContent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProviderCreateComponent {
  private readonly providerService = inject(ProviderService);
  private readonly router = inject(Router);
  readonly create$ = new Subject<void>();
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.providerService.isLoadingSignal;
  @ViewChild('form') form!: ProviderFormComponent;

  constructor(public dialogRef: MatDialogRef<ProviderCreateComponent>) {
    this.gotToList$.pipe(takeUntilDestroyed()).subscribe(() => {
      this.dialogRef.close({ shouldReload: true });
    });

    this.create$
      .pipe(
        filter(() => this.form.isValid),
        switchMap(() => this.providerService.save$(this.form.value)),
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
