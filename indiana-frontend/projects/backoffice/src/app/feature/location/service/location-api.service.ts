import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocationListDto } from '../dto/location-list.dto';
import { LocationFilterDto } from '../dto/location-filter.dto';
import { environment } from '../../../../environments/environment';
import { LocationDto } from '../dto/location.dto';

@Injectable()
export class LocationApiService {
  private readonly http: HttpClient = inject(HttpClient);

  private readonly basePath: string = environment.apiUrl + '/locations';

  getAll$(dto: LocationFilterDto | null = null): Observable<LocationListDto> {
    const page: number = dto?.page ? dto.page : 0;
    const pageSize: number = dto?.pageSize ? dto.pageSize : 2;
    const sortBy: string = dto?.sortBy ? dto.sortBy : 'id';
    const sortOrder: string = dto?.sortBy ? dto.sortOrder : 'ASC';
    const query: string | null = dto?.query ? dto.query : '';
    return this.http.get<LocationListDto>(
      this.basePath +
        `?page=${page}&pageSize=${pageSize}&sortBy=${sortBy}&sortOrder=${sortOrder}&query=${query}`,
    );
  }

  save$(dto: LocationDto): Observable<LocationDto> {
    return this.http.post<LocationDto>(this.basePath, { ...dto });
  }

  update$(id: string, dto: LocationDto): Observable<LocationDto> {
    return this.http.put<LocationDto>(`${this.basePath}/${id}`, { ...dto });
  }

  getById$(id: string): Observable<LocationDto> {
    return this.http.get<LocationDto>(`${this.basePath}/${id}`);
  }
}
