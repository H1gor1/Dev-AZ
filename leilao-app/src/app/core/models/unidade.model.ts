export interface CreateUnidadeRequest {
  nome: string;
}

export interface UpdateUnidadeRequest {
  nome?: string;
}

export interface UnidadeResponse {
  id: number;
  nome: string;
  createdAt: string;
  updatedAt: string;
}
