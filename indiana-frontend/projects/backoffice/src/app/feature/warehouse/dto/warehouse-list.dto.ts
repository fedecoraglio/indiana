import { WarehouseDto } from './warehouse.dto';

export type WarehouseListDto = Readonly<{
  totalElements: number;
  totalPages: number;
  pageSize: number;
  page: number;
  numberOfElements: number;
  content: WarehouseDto[];
}>;
