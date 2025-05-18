export type WarehouseDto = Readonly<{
  id?: number | null | undefined;
  name?: string | null | undefined;
  locationId: number | null | undefined;
  location?: WarehouseLocationDto | null | undefined;
}>;

export type WarehouseLocationDto = Readonly<{
  id?: number | null | undefined;
  name?: string | null | undefined;
}>
