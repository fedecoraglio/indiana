import { Routes } from '@angular/router';

import { LocationService } from '../../core/service/location/location.service';
import { LocationApiService } from '../../core/service/location/location-api.service';
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
