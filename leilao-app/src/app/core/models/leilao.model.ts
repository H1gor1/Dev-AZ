import { EmpresaResponse } from './empresa.model';
import { LoteResponse } from './lote.model';

export interface CreateLeilaoRequest {
  codigo: number;
  descricao: string;
  vendedorId: number;
  inicioPrevisto: string;
}

export interface LeilaoUpdateRequest {
  codigo?: number;
  descricao?: string;
  inicioPrevisto?: string;
}

export interface LeilaoResponse {
  id: number;
  codigo: number;
  descricao: string;
  vendedor: EmpresaResponse;
  inicioPrevisto: string;
  total: number;
  lotes?: LoteResponse[];
  createdAt: string;
  updatedAt: string;
}
