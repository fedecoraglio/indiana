import { Routes } from '@angular/router';

import { ProviderComponent } from './provider.component';
import { ProviderListComponent } from './list/provider-list.component';
import { ProviderService } from '../../core/service/provider/provider.service';
import { ProviderApiService } from '../../core/service/provider/provider-api.service';

export default [
  {
    path: '',
    providers: [ProviderService, ProviderApiService],
    component: ProviderComponent,
    children: [
      {
        path: '',
        component: ProviderListComponent
      },
    ],
  },
] as Routes;
