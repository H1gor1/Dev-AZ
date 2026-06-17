import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import {
  CreateUnidadeRequest,
  UpdateUnidadeRequest,
  UnidadeResponse,
} from '../models/unidade.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UnidadeService {
  private readonly apiUrl = environment.apiUrl + '/unidades';

  constructor(private http: HttpClient) {}

  listar(page: number = 0, size: number = 10, sortField?: string, sortOrder?: number, nome?: string): Observable<ApiResponse<UnidadeResponse[]>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (sortField) {
      const dir = sortOrder === -1 ? 'desc' : 'asc';
      params = params.set('sort', `${sortField},${dir}`);
    }

    if (nome) {
      params = params.set('nome', nome);
    }

    return this.http.get<ApiResponse<UnidadeResponse[]>>(this.apiUrl, { params });
  }

  buscarPorId(id: number): Observable<ApiResponse<UnidadeResponse>> {
    return this.http.get<ApiResponse<UnidadeResponse>>(`${this.apiUrl}/${id}`);
  }

  criar(request: CreateUnidadeRequest): Observable<ApiResponse<UnidadeResponse>> {
    return this.http.post<ApiResponse<UnidadeResponse>>(this.apiUrl, request);
  }

  atualizar(id: number, request: UpdateUnidadeRequest): Observable<ApiResponse<UnidadeResponse>> {
    return this.http.put<ApiResponse<UnidadeResponse>>(`${this.apiUrl}/${id}`, request);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
