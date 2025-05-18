import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WarehouseListDto } from '../../../feature/warehouse/dto/warehouse-list.dto';
import { WarehouseFilterDto } from '../../../feature/warehouse/dto/warehouse-filter.dto';
import { environment } from '../../../../environments/environment';
import { WarehouseDto } from '../../../feature/warehouse/dto/warehouse.dto';

@Injectable()
export class WarehouseApiService {
  private readonly http: HttpClient = inject(HttpClient);

  private readonly basePath: string = environment.apiUrl + '/warehouses';

  getAll$(dto: WarehouseFilterDto | null = null): Observable<WarehouseListDto> {
    const page: number = dto?.page ? dto.page : 0;
    const pageSize: number = dto?.pageSize ? dto.pageSize : 2;
    const sortBy: string = dto?.sortBy ? dto.sortBy : 'id';
    const sortOrder: string = dto?.sortBy ? dto.sortOrder : 'ASC';
    const query: string | null = dto?.query ? dto.query : '';
    return this.http.get<WarehouseListDto>(
      this.basePath +
        `?page=${page}&pageSize=${pageSize}&sortBy=${sortBy}&sortOrder=${sortOrder}&query=${query}`,
    );
  }

  save$(dto: WarehouseDto): Observable<WarehouseDto> {
    return this.http.post<WarehouseDto>(this.basePath, { ...dto });
  }

  update$(id: string, dto: WarehouseDto): Observable<WarehouseDto> {
    return this.http.put<WarehouseDto>(`${this.basePath}/${id}`, { ...dto });
  }

  getById$(id: string): Observable<WarehouseDto> {
    return this.http.get<WarehouseDto>(`${this.basePath}/${id}`);
  }
}
