export type LocationFilterDto = Readonly<{
  page: number;
  pageSize: number;
  sortBy: string;
  sortOrder: string;
  query?: string | null | undefined;
}>;
