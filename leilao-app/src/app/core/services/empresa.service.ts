import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import {
  EmpresaRequest,
  EmpresaUpdateRequest,
  EmpresaResponse,
} from '../models/empresa.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class EmpresaService {
  private readonly apiUrl = environment.apiUrl + '/empresa';

  constructor(private http: HttpClient) {}

  listar(page: number = 0, size: number = 10): Observable<ApiResponse<EmpresaResponse[]>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<ApiResponse<EmpresaResponse[]>>(this.apiUrl, { params });
  }

  buscarPorId(id: number): Observable<ApiResponse<EmpresaResponse>> {
    return this.http.get<ApiResponse<EmpresaResponse>>(`${this.apiUrl}/${id}`);
  }

  criar(request: EmpresaRequest): Observable<ApiResponse<EmpresaResponse>> {
    return this.http.post<ApiResponse<EmpresaResponse>>(this.apiUrl, request);
  }

  atualizar(id: number, request: EmpresaUpdateRequest): Observable<ApiResponse<EmpresaResponse>> {
    return this.http.put<ApiResponse<EmpresaResponse>>(`${this.apiUrl}/${id}`, request);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
