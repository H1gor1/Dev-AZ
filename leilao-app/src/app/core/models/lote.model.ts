import { UnidadeResponse } from './unidade.model';
import { LeilaoResponse } from './leilao.model';

export interface CreateLoteRequest {
  numeroLote: number;
  descricao: string;
  quantidade: number;
  valorInicial: number;
  unidadeId: number;
  leilaoId: number;
}

export interface UpdateLoteRequest {
  numeroLote?: number;
  descricao?: string;
  quantidade?: number;
  valorInicial?: number;
  unidadeId?: number;
}

export interface LoteResponse {
  id: number;
  numeroLote: number;
  descricao: string;
  quantidade: number;
  valorInicial: number;
  unidade: UnidadeResponse;
  leilao: LeilaoResponse;
  createdAt: string;
  updatedAt: string;
}
