import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { TableLazyLoadEvent } from 'primeng/table';
import { EmpresaResponse } from '../../core/models/empresa.model';
import { EmpresaService } from '../../core/services/empresa.service';
import { DataTable, Column, Action } from '../../shared/components/data-table/data-table';
import { CnpjPipe } from '../../shared/pipes/cnpj.pipe';
import { TelefonePipe } from '../../shared/pipes/telefone.pipe';

@Component({
  selector: 'app-empresas',
  imports: [DataTable, ButtonModule, RouterLink],
  templateUrl: './empresas.html',
})
export class Empresas {
  private service = inject(EmpresaService);
  private cs = inject(ConfirmationService);
  private cnpjPipe = new CnpjPipe();
  private fonePipe = new TelefonePipe();

  items = signal<EmpresaResponse[]>([]);
  loading = signal(false);
  totalRecords = signal(0);

  cols: Column[] = [
    { field: 'cnpj', header: 'CNPJ', format: (v) => this.cnpjPipe.transform(v) },
    { field: 'razaoSocial', header: 'Razão Social' },
    { field: 'telefone', header: 'Telefone', format: (v) => this.fonePipe.transform(v) },
    { field: 'email', header: 'Email' },
  ];

  actions: Action<EmpresaResponse>[] = [
    { icon: 'pencil', label: 'Editar', severity: 'info', onClick: (row) => this.editar(row) },
    { icon: 'trash', label: 'Excluir', severity: 'danger', onClick: (row) => this.excluir(row) },
  ];

  carregar(event: TableLazyLoadEvent): void {
    this.loading.set(true);
    const page = (event.first ?? 0) / (event.rows ?? 10);
    this.service
      .listar(page, event.rows ?? 10, event.sortField as string, event.sortOrder as number)
      .subscribe((res) => {
        this.items.set(res.data);
        this.totalRecords.set(res.metadata?.totalElements ?? 0);
        this.loading.set(false);
      });
  }

  editar(row: EmpresaResponse): void {
    // TODO: navegar pra /empresa/:id
  }

  excluir(row: EmpresaResponse): void {
    this.cs.confirm({
      header: 'Excluir',
      message: `Excluir empresa ${row.razaoSocial}?`,
      rejectLabel: 'Cancelar',
      acceptLabel: 'Excluir',
      accept: () => {
        this.service.excluir(row.id).subscribe(() => this.carregar({ first: 0, rows: 10 }));
      },
    });
  }
}
