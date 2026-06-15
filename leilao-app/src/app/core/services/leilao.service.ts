import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import {
  CreateLeilaoRequest,
  LeilaoUpdateRequest,
  LeilaoResponse,
} from '../models/leilao.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LeilaoService {
  private readonly apiUrl = environment.apiUrl + '/leilao';

  constructor(private http: HttpClient) {}

  listar(page: number = 0, size: number = 10): Observable<ApiResponse<LeilaoResponse[]>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<ApiResponse<LeilaoResponse[]>>(this.apiUrl, { params });
  }

  buscarPorId(id: number): Observable<ApiResponse<LeilaoResponse>> {
    return this.http.get<ApiResponse<LeilaoResponse>>(`${this.apiUrl}/${id}`);
  }

  criar(request: CreateLeilaoRequest): Observable<ApiResponse<LeilaoResponse>> {
    return this.http.post<ApiResponse<LeilaoResponse>>(this.apiUrl, request);
  }

  atualizar(id: number, request: LeilaoUpdateRequest): Observable<ApiResponse<LeilaoResponse>> {
    return this.http.put<ApiResponse<LeilaoResponse>>(`${this.apiUrl}/${id}`, request);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
