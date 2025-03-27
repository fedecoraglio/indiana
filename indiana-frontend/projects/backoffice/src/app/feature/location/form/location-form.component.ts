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
import {LocationFormField} from "./field/location-form-field";
import {LocationDto} from "../dto/location.dto";

@Component({
  selector: 'indiana-backoffice-location-form',
  templateUrl: './location-form.component.html',
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
export class LocationFormComponent implements OnDestroy, AfterViewInit {
  private readonly destroy$ = new Subject<void>();
  readonly fields = LocationFormField;
  readonly formGroup = new FormGroup({
    [LocationFormField.Name]: new FormControl('', Validators.required),
    [LocationFormField.Address]: new FormControl('', Validators.required),
  });
  readonly location$ = new ReplaySubject<LocationDto | null>(1);
  @Input() isReadonly = false;
  @Input() set location(dto: LocationDto | null) {
    this.location$.next(dto);
  }

  get nameField() {
    return this.formGroup.get(LocationFormField.Name);
  }

  get isValid() {
    this.formGroup.markAllAsTouched();

    return this.formGroup.valid;
  }

  get value(): LocationDto {
    return {
      ...(this.formGroup.getRawValue() as LocationDto),
    };
  }

  ngOnDestroy() {
    this.destroy$.next();
  }

  ngAfterViewInit() {
    this.location$.pipe(
        filter(Boolean),
        takeUntil(this.destroy$)).subscribe((dto: LocationDto) => {
      this.formGroup.patchValue(dto);
      if(this.isReadonly) {
        this.formGroup.disable();
      }
      this.formGroup.updateValueAndValidity();
    });
  }
}
