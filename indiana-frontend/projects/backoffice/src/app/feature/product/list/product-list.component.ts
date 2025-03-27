import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { ProductService } from '../service/product.service';
import { Subject, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AsyncPipe } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';

import { ProductListTableComponent } from './list-table/product-list-table.component';
import { ProductFilterDto } from '../dto/product-filter.dto';
import {MatButtonToggle} from "@angular/material/button-toggle";

@Component({
  selector: 'indiana-backoffice-product-list',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule,
    ProductListTableComponent,
    MatTableModule,
    MatSortModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    RouterLink
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ProductListComponent {
  private readonly productService = inject(ProductService);
  private readonly showProductList$ = new Subject<ProductFilterDto | null>();
  private readonly defaultProductFilter: ProductFilterDto = {
    page: 0,
    pageSize: 30,
    sortBy: 'id',
    sortOrder: 'desc',
  };
  productListSignal = this.productService.productListSignal;

  constructor() {
    this.showProductList$
      .pipe(
        switchMap((dto) => this.productService.getAll$(dto)),
        takeUntilDestroyed(),
      )
      .subscribe(() => {
        console.log("llama!!")
      });
    this.showProductList$.next(this.defaultProductFilter);
  }

  onProductSelected($event: any) {
    this.showProductList$.next(
      ($event as ProductFilterDto | null) ?? this.defaultProductFilter,
    );
  }

  searchProduct($event: any) {
    this.showProductList$.next(
      ($event as ProductFilterDto | null) ?? this.defaultProductFilter,
    );
  }
}
