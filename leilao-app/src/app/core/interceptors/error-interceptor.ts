import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {MessageService} from 'primeng/api';
import {catchError, throwError} from 'rxjs';
import {ApiErrorResponse} from '../models/api-response.model';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const messageService = inject(MessageService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      const body = error.error as ApiErrorResponse;
      const detail = body?.message ?? 'Erro inesperado';

      messageService.add({
        severity: 'error',
        summary: 'Erro',
        detail,
        life: 5000
      });

      return throwError(() => error);
    })
  );
};
