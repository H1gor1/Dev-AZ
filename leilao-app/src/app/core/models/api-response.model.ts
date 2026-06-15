export interface PageMetadata {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface ApiResponse<T> {
  message: string;
  data: T;
  metadata: PageMetadata | null;
}

export interface ApiErrorResponse {
  error: string;
  message: string;
  path: string;
  timestamp: string;
  details: Record<string, string> | null;
}
