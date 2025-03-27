import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductListDto } from '../dto/product-list.dto';
import { ProductFilterDto } from '../dto/product-filter.dto';
import { environment } from '../../../../environments/environment';
import { ProductDto } from '../dto/product.dto';

@Injectable()
export class ProductApiService {
  private readonly http: HttpClient = inject(HttpClient);

  private readonly basePath: string = environment.apiUrl + '/products';

  getAll$(dto: ProductFilterDto | null = null): Observable<ProductListDto> {
    const page: number = dto?.page ? dto.page : 0;
    const pageSize: number = dto?.pageSize ? dto.pageSize : 2;
    const sortBy: string = dto?.sortBy ? dto.sortBy : 'id';
    const sortOrder: string = dto?.sortBy ? dto.sortOrder : 'ASC';
    const query: string | null = dto?.query ? dto.query : '';
    console.log("this.basePath", this.basePath);
    return this.http.get<ProductListDto>(
      this.basePath +
        `?page=${page}&pageSize=${pageSize}&sortBy=${sortBy}&sortOrder=${sortOrder}&query=${query}`,
    );
  }

  save$(dto: ProductDto): Observable<ProductDto> {
    return this.http.post<ProductDto>(this.basePath, { ...dto });
  }

  update$(id: string, dto: ProductDto): Observable<ProductDto> {
    return this.http.put<ProductDto>(`${this.basePath}/${id}`, { ...dto });
  }

  getById$(id: string): Observable<ProductDto> {
    return this.http.get<ProductDto>(`${this.basePath}/${id}`);
  }
}
