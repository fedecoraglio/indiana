import { ProviderDto } from './provider.dto';

export type ProviderListDto = Readonly<{
  totalElements: number;
  totalPages: number;
  pageSize: number;
  page: number;
  numberOfElements: number;
  content: ProviderDto[];
}>;
