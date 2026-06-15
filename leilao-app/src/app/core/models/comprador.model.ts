export interface CreateCompradorRequest {
  empresaId: number;
  leilaoId: number;
}

export interface CompradorResponse {
  empresaId: number;
  razaoSocial: string;
  leilaoId: number;
  descricaoLeilao: string;
}
