import { ProductDto } from './product.dto';

export type ProductListDto = Readonly<{
  totalElements: number;
  totalPages: number;
  pageSize: number;
  page: number;
  numberOfElements: number;
  content: ProductDto[];
}>;
