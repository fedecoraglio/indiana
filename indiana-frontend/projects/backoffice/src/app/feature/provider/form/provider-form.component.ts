import {
  AfterViewInit, ChangeDetectionStrategy,
  Component,
  Input, OnDestroy
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {filter, ReplaySubject, Subject, takeUntil} from "rxjs";
import {ProviderFormField} from "./field/provider-form-field";
import {ProviderDto} from "../dto/provider.dto";

@Component({
  selector: 'indiana-backoffice-provider-form',
  templateUrl: './provider-form.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProviderFormComponent implements OnDestroy, AfterViewInit {
  private readonly destroy$ = new Subject<void>();
  readonly fields = ProviderFormField;
  readonly formGroup = new FormGroup({
    [ProviderFormField.Name]: new FormControl('', Validators.required),
    [ProviderFormField.Address]: new FormControl('', Validators.required),
  });
  readonly provider$ = new ReplaySubject<ProviderDto | null>(1);
  @Input() isReadonly = false;
  @Input() set provider(dto: ProviderDto | null) {
    this.provider$.next(dto);
  }

  get nameField() {
    return this.formGroup.get(ProviderFormField.Name);
  }

  get isValid() {
    this.formGroup.markAllAsTouched();

    return this.formGroup.valid;
  }

  get value(): ProviderDto {
    return {
      ...(this.formGroup.getRawValue() as ProviderDto),
    };
  }

  ngOnDestroy() {
    this.destroy$.next();
  }

  ngAfterViewInit() {
    this.provider$.pipe(
        filter(Boolean),
        takeUntil(this.destroy$)).subscribe((dto: ProviderDto) => {
      this.formGroup.patchValue(dto);
      if(this.isReadonly) {
        this.formGroup.disable();
      }
      this.formGroup.updateValueAndValidity();
    });
  }
}
