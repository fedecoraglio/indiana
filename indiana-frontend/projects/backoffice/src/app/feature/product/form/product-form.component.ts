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
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {filter, ReplaySubject, Subject, takeUntil} from "rxjs";
import {ProductFormField} from "./field/product-form-field";
import {ProductDto} from "../dto/product.dto";

@Component({
  selector: 'indiana-backoffice-product-form',
  templateUrl: './product-form.component.html',
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
export class ProductFormComponent implements OnDestroy, AfterViewInit {
  private readonly destroy$ = new Subject<void>();
  readonly fields = ProductFormField;
  readonly formGroup = new FormGroup({
    [ProductFormField.Name]: new FormControl('', Validators.required),
    [ProductFormField.Description]: new FormControl(''),
    [ProductFormField.Code]: new FormControl('', Validators.required),
    [ProductFormField.Barcode]: new FormControl('', Validators.required),
  });
  readonly product$ = new ReplaySubject<ProductDto | null>(1);
  @Input() isReadonly = false;
  @Input() set product(product: ProductDto | null) {
    this.product$.next(product);
  }

  get nameField() {
    return this.formGroup.get(ProductFormField.Name);
  }

  get codeField() {
    return this.formGroup.get(ProductFormField.Code);
  }

  get barcodeField() {
    return this.formGroup.get(ProductFormField.Barcode);
  }

  get isValid() {
    this.formGroup.markAllAsTouched();

    return this.formGroup.valid;
  }

  get value(): ProductDto {
    return {
      ...(this.formGroup.getRawValue() as ProductDto),
    };
  }

  ngOnDestroy() {
    this.destroy$.next();
  }

  ngAfterViewInit() {
    this.product$.pipe(
        filter(Boolean),
        takeUntil(this.destroy$)).subscribe((product: ProductDto) => {
      this.formGroup.patchValue(product);
      if(this.isReadonly) {
        this.formGroup.disable();
      }
      this.formGroup.updateValueAndValidity();
    });
  }
}
