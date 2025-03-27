import { LocationDto } from './location.dto';

export type LocationListDto = Readonly<{
  totalElements: number;
  totalPages: number;
  pageSize: number;
  page: number;
  numberOfElements: number;
  content: LocationDto[];
}>;
