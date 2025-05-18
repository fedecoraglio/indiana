import { Routes } from '@angular/router';

import { WarehouseService } from '../../core/service/warehouse/warehouse.service';
import { WarehouseApiService } from '../../core/service/warehouse/warehouse-api.service';
import { WarehouseComponent } from './warehouse.component';
import { WarehouseListComponent } from './list/warehouse-list.component';
import {LocationService} from "../../core/service/location/location.service";
import {LocationApiService} from "../../core/service/location/location-api.service";

export default [
  {
    path: '',
    providers: [WarehouseService, WarehouseApiService, LocationService, LocationApiService],
    component: WarehouseComponent,
    children: [
      {
        path: '',
        component: WarehouseListComponent
      },
    ],
  },
] as Routes;
