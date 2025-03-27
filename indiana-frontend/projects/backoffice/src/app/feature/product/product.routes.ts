import { Routes } from '@angular/router';

import { ProductService } from './service/product.service';
import { ProductApiService } from './service/product-api.service';
import { ProductComponent } from './product.component';
import { ProductListComponent } from './list/product-list.component';
import { ProductCreateComponent } from './create/product-create.component';
import {ProductEditComponent} from "./edit/product-edit.component";
import {ProductViewComponent} from "./view/product-view.component";

export default [
  {
    path: '',
    providers: [ProductService, ProductApiService],
    component: ProductComponent,
    children: [
      {
        path: '',
        component: ProductListComponent
      },
      {
        path: 'create',
        component: ProductCreateComponent,
      },
      {
        path: 'edit/:id',
        component: ProductEditComponent,
      },
      {
        path: 'view/:id',
        component: ProductViewComponent,
      },
    ],
  },
] as Routes;
