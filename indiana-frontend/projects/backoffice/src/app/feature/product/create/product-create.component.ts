import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ProductService } from '../service/product.service';
import { Router } from '@angular/router';
import { filter, from, Subject, switchMap } from 'rxjs';
import { ProductFormComponent } from '../form/product-form.component';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'indiana-backoffice-product-create',
  templateUrl: './product-create.component.html',
  providers: [],
  standalone: true,
  imports: [ProductFormComponent, MatButtonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductCreateComponent {
  private readonly productService = inject(ProductService);
  private readonly router = inject(Router);
  readonly create$ = new Subject<void>();
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.productService.isLoadingSignal;
  @ViewChild('productForm') productForm!: ProductFormComponent;

  constructor() {
    this.gotToList$
      .pipe(
        switchMap(() => from(this.router.navigate(['/product']))),
        takeUntilDestroyed(),
      )
      .subscribe();

    this.create$
      .pipe(
        filter(() => this.productForm.isValid),
        switchMap(() => this.productService.save$(this.productForm.value)),
        takeUntilDestroyed()
      )
      .subscribe(() => {
        this.gotToList$.next();
      });
  }
}
