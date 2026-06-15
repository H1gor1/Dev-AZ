import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { CompradorResponse, CreateCompradorRequest } from '../models/comprador.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CompradorService {
  private readonly apiUrl = environment.apiUrl + '/comprador';

  constructor(private http: HttpClient) {}

  listar(page: number = 0, size: number = 10): Observable<ApiResponse<CompradorResponse[]>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<ApiResponse<CompradorResponse[]>>(this.apiUrl, { params });
  }

  buscarPorLeilao(leilaoId: number): Observable<ApiResponse<CompradorResponse[]>> {
    return this.http.get<ApiResponse<CompradorResponse[]>>(`${this.apiUrl}/leilao/${leilaoId}`);
  }

  buscarPorEmpresa(empresaId: number): Observable<ApiResponse<CompradorResponse[]>> {
    return this.http.get<ApiResponse<CompradorResponse[]>>(`${this.apiUrl}/empresa/${empresaId}`);
  }

  criar(request: CreateCompradorRequest): Observable<ApiResponse<CompradorResponse>> {
    return this.http.post<ApiResponse<CompradorResponse>>(this.apiUrl, request);
  }

  excluir(empresaId: number, leilaoId: number): Observable<void> {
    const params = new HttpParams()
      .set('empresaId', empresaId.toString())
      .set('leilaoId', leilaoId.toString());

    return this.http.delete<void>(this.apiUrl, { params });
  }
}
