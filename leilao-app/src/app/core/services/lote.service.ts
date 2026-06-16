import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import {
  CreateLoteRequest,
  UpdateLoteRequest,
  LoteResponse,
} from '../models/lote.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoteService {
  private readonly apiUrl = environment.apiUrl + '/lote';

  constructor(private http: HttpClient) {}

  listar(page: number = 0, size: number = 10, leilaoId?: number): Observable<ApiResponse<LoteResponse[]>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (leilaoId != null) {
      params = params.set('leilaoId', leilaoId.toString());
    }

    return this.http.get<ApiResponse<LoteResponse[]>>(this.apiUrl, { params });
  }

  buscarPorId(id: number): Observable<ApiResponse<LoteResponse>> {
    return this.http.get<ApiResponse<LoteResponse>>(`${this.apiUrl}/${id}`);
  }

  criar(request: CreateLoteRequest): Observable<ApiResponse<LoteResponse>> {
    return this.http.post<ApiResponse<LoteResponse>>(this.apiUrl, request);
  }

  atualizar(id: number, request: UpdateLoteRequest): Observable<ApiResponse<LoteResponse>> {
    return this.http.put<ApiResponse<LoteResponse>>(`${this.apiUrl}/${id}`, request);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
