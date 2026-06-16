import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'cep', standalone: true })
export class CepPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) return '';
    const d = value.replace(/\D/g, '').padStart(8, '0').slice(0, 8);
    return d.replace(/^(\d{5})(\d{3})$/, '$1-$2');
  }
}
