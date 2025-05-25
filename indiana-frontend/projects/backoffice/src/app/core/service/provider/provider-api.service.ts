import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ProviderFilterDto } from '../../../feature/provider/dto/provider-filter.dto';
import { ProviderListDto } from '../../../feature/provider/dto/provider-list.dto';
import { ProviderDto } from '../../../feature/provider/dto/provider.dto';

@Injectable({ providedIn: 'root' })
export class ProviderApiService {
  private readonly http: HttpClient = inject(HttpClient);

  private readonly basePath: string = environment.apiUrl + '/providers';

  getAll$(dto: ProviderFilterDto | null = null): Observable<ProviderListDto> {
    const page: number = dto?.page ? dto.page : 0;
    const pageSize: number = dto?.pageSize ? dto.pageSize : 2;
    const sortBy: string = dto?.sortBy ? dto.sortBy : 'id';
    const sortOrder: string = dto?.sortBy ? dto.sortOrder : 'ASC';
    const query: string | null = dto?.query ? dto.query : '';
    return this.http.get<ProviderListDto>(
      this.basePath +
        `?page=${page}&pageSize=${pageSize}&sortBy=${sortBy}&sortOrder=${sortOrder}&query=${query}`,
    );
  }

  save$(dto: ProviderDto): Observable<ProviderDto> {
    return this.http.post<ProviderDto>(this.basePath, { ...dto });
  }

  update$(id: string, dto: ProviderDto): Observable<ProviderDto> {
    return this.http.put<ProviderDto>(`${this.basePath}/${id}`, { ...dto });
  }

  getById$(id: string): Observable<ProviderDto> {
    return this.http.get<ProviderDto>(`${this.basePath}/${id}`);
  }
}
