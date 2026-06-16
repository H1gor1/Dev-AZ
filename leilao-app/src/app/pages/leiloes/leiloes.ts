import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { TableLazyLoadEvent } from 'primeng/table';
import { LeilaoResponse } from '../../core/models/leilao.model';
import { LeilaoService } from '../../core/services/leilao.service';
import { DataTable, Column, Action } from '../../shared/components/data-table/data-table';

@Component({
  selector: 'app-leiloes',
  imports: [DataTable, ButtonModule, RouterLink],
  templateUrl: './leiloes.html',
})
export class Leiloes {
  private service = inject(LeilaoService);
  private cs = inject(ConfirmationService);
  private router = inject(Router);

  items = signal<LeilaoResponse[]>([]);
  loading = signal(false);
  totalRecords = signal(0);

  cols: Column[] = [
    { field: 'vendedor', header: 'Vendedor', format: (v) => v?.razaoSocial ?? '' },
    { field: 'inicioPrevisto', header: 'Início Previsto', format: (v) => v ? new Date(v).toLocaleString('pt-BR') : '' },
    { field: 'total', header: 'Total', format: (v) => new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v ?? 0) },
  ];

  actions: Action<LeilaoResponse>[] = [
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

  editar(row: LeilaoResponse): void {
    this.router.navigate(['/leilao', row.id]);
  }

  excluir(row: LeilaoResponse): void {
    this.cs.confirm({
      header: 'Excluir',
      message: `Excluir leilão "${row.descricao}"?`,
      rejectLabel: 'Cancelar',
      acceptLabel: 'Excluir',
      accept: () => {
        this.service.excluir(row.id).subscribe(() => this.carregar({ first: 0, rows: 10 }));
      },
    });
  }
}
