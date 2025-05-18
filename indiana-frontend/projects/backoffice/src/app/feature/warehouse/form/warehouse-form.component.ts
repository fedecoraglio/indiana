import {
  AfterViewInit, ChangeDetectionStrategy,
  Component, inject,
  Input, OnDestroy
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatSelectModule } from "@angular/material/select";
import { MatInputModule } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";
import { filter, ReplaySubject, Subject, takeUntil } from "rxjs";
import { WarehouseFormField } from "./field/warehouse-form-field";
import { WarehouseDto } from "../dto/warehouse.dto";
import { LocationService } from "../../../core/service/location/location.service";
import { CommonModule } from "@angular/common";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";

@Component({
  selector: 'indiana-backoffice-warehouse-form',
  templateUrl: './warehouse-form.component.html',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WarehouseFormComponent implements OnDestroy, AfterViewInit {
  private readonly destroy$ = new Subject<void>();
  private readonly locationService = inject(LocationService);
  readonly fields = WarehouseFormField;
  readonly formGroup = new FormGroup({
    [WarehouseFormField.Name]: new FormControl('', Validators.required),
    [WarehouseFormField.Location]: new FormControl('', Validators.required),
  });
  readonly location$ = new ReplaySubject<WarehouseDto | null>(1);
  readonly locationListSignal = this.locationService.locationListSignal;
  @Input() isReadonly = false;

  @Input() set location(dto: WarehouseDto | null) {
    this.location$.next(dto);
  }

  get nameField() {
    return this.formGroup.get(WarehouseFormField.Name);
  }

  get locationField() {
    return this.formGroup.get(WarehouseFormField.Location);
  }

  get isValid() {
    this.formGroup.markAllAsTouched();

    return this.formGroup.valid;
  }

  get value(): WarehouseDto {
    return {
      ...(this.formGroup.getRawValue() as any),
    };
  }

  constructor() {
    this.locationService.getAll$({
      page: 0,
      pageSize: 300,
      sortBy: 'id',
      sortOrder: 'desc'
    }).pipe(takeUntilDestroyed()).subscribe();
  }

  ngOnDestroy() {
    this.destroy$.next();
  }

  ngAfterViewInit() {
    this.location$.pipe(filter(Boolean), takeUntil(this.destroy$))
    .subscribe((dto: WarehouseDto) => {
      this.formGroup.patchValue({...dto, locationId: dto.location?.id} as any);
      if (this.isReadonly) {
        this.formGroup.disable();
      }
      this.formGroup.updateValueAndValidity();
    });
  }
}
