import {
  ChangeDetectionStrategy,
  Component,
  Inject,
  inject,
  ViewChild,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Subject } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent, MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';

import { ProviderFormComponent } from '../form/provider-form.component';
import { ProviderService } from "../../../core/service/provider/provider.service";
import { ProviderApiService } from "../../../core/service/provider/provider-api.service";

@Component({
  selector: 'indiana-backoffice-provider-edit',
  templateUrl: './provider-view.component.html',
  providers: [ProviderService, ProviderApiService],
  standalone: true,
  imports: [
    ProviderFormComponent,
    MatButtonModule,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProviderViewComponent {
  private readonly providerService = inject(ProviderService);
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.providerService.isLoadingSignal;
  readonly providerSignal = this.providerService.providerSignal;
  @ViewChild('form') form!: ProviderFormComponent;

  constructor(
      public dialogRef: MatDialogRef<ProviderViewComponent>,
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
      this.dialogRef.close({shouldReload: true});
    });
  }

  cancel() {
    this.dialogRef.close({shouldReload: false});
  }
}
