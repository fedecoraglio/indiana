import { Routes } from '@angular/router';

import { LocationService } from './service/location.service';
import { LocationApiService } from './service/location-api.service';
import { LocationComponent } from './location.component';
import { LocationListComponent } from './list/location-list.component';

export default [
  {
    path: '',
    providers: [LocationService, LocationApiService],
    component: LocationComponent,
    children: [
      {
        path: '',
        component: LocationListComponent
      },
    ],
  },
] as Routes;
