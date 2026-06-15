export interface EmpresaRequest {
  razaoSocial: string;
  cnpj: string;
  logradouro?: string;
  municipio?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  cep?: string;
  telefone?: string;
  email: string;
  site?: string;
  usuario: string;
  password: string;
}

export interface EmpresaUpdateRequest {
  razaoSocial?: string;
  cnpj?: string;
  logradouro?: string;
  municipio?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  cep?: string;
  telefone?: string;
  email?: string;
  site?: string;
  usuario?: string;
}

export interface EmpresaResponse {
  id: number;
  razaoSocial: string;
  cnpj: string;
  logradouro: string;
  municipio: string;
  numero: string;
  complemento: string;
  bairro: string;
  cep: string;
  telefone: string;
  email: string;
  site: string;
  usuario: string;
  createdAt: string;
  updatedAt: string;
}
