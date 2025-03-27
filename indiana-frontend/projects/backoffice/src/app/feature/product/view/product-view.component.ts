import {
  ChangeDetectionStrategy,
  Component,
  inject,
  Signal,
  ViewChild,
} from '@angular/core';
import { ProductService } from '../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, from, map, Subject, switchMap, takeUntil, tap } from 'rxjs';
import { ProductFormComponent } from '../form/product-form.component';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ProductDto } from '../dto/product.dto';

@Component({
  selector: 'indiana-backoffice-product-edit',
  templateUrl: './product-view.component.html',
  providers: [],
  standalone: true,
  imports: [ProductFormComponent, MatButtonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductViewComponent {
  private readonly productService = inject(ProductService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  readonly gotToList$ = new Subject<void>();
  readonly isLoadingSignal = this.productService.isLoadingSignal;
  readonly id$ = this.route.params.pipe(map(({ id }) => id));
  readonly productSignal = this.productService.productSignal;
  @ViewChild('productForm') productForm!: ProductFormComponent;

  constructor() {
    this.id$
      .pipe(switchMap((id) => this.productService.getById$(id)))
      .subscribe();

    this.gotToList$
      .pipe(
        switchMap(() => from(this.router.navigate(['/product']))),
        takeUntilDestroyed(),
      )
      .subscribe();
  }
}
