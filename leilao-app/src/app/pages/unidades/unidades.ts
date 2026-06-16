import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTable, Column, Action, CellEditEvent } from '../../shared/components/data-table/data-table';
import { TableLazyLoadEvent } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ConfirmationService, MessageService } from 'primeng/api';
import { UnidadeService } from '../../core/services/unidade.service';
import { UnidadeResponse, UpdateUnidadeRequest } from '../../core/models/unidade.model';


@Component({
  selector: 'app-unidades',
  imports: [DataTable, ButtonModule, DialogModule, InputTextModule, FormsModule],
  templateUrl: './unidades.html',
})
export class Unidades {
  private service = inject(UnidadeService);
  private cs = inject(ConfirmationService);
  private ms = inject(MessageService);

  items = signal<UnidadeResponse[]>([]);
  loading = signal(false);
  totalRecords = signal(0);

  dialogVisible = false;
  novoNome = '';

  cols: Column[] = [
    {field: 'nome', header: "Nome", editable: true, maxLength: 128},
    {field: 'createdAt', header: "Data de criação"},
    {field: 'updatedAt', header: "Data de atualização"},
  ]

  actions: Action<UnidadeResponse>[] = [
    {icon: 'trash', label: 'Excluir', severity: 'danger', onClick: (row) => this.excluir(row)}
  ]

  showDialog(): void {
    this.novoNome = '';
    this.dialogVisible = true;
  }

  salvar(): void {
    if (!this.novoNome.trim()) {
      this.ms.add({ severity: 'warn', summary: 'Aviso', detail: 'Nome não pode estar vazio' });
      return;
    }

    this.service.criar({ nome: this.novoNome.trim() }).subscribe({
      next: () => {
        this.ms.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Unidade criada com sucesso'
        });
        this.dialogVisible = false;
        this.carregar({ first: 0, rows: 10 });
      }
    });
  }

  carregar(event: TableLazyLoadEvent): void {
    this.loading.set(true);
    const page = (event.first ?? 0) / (event.rows ?? 10);
    this.service.listar(page, event.rows ?? 10, event.sortField as string, event.sortOrder as number)
      .subscribe((res) => {
        this.items.set(res.data);
        this.totalRecords.set(res.metadata?.totalElements ?? 0);
        this.loading.set(false);
      });
  }

  onUpdateEvent(event: CellEditEvent<UnidadeResponse>){
    const nome = event.rowData.nome?.trim() ?? '';
    if (!nome) {
      this.ms.add({ severity: 'warn', summary: 'Aviso', detail: 'Nome não pode estar vazio' });
      this.carregar({ first: 0, rows: 10 });
      return;
    }
    const request: UpdateUnidadeRequest = { nome }
    this.service
      .atualizar(event.rowData.id, request)
      .subscribe({
        next: () => {
          this.ms.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Unidade atualizada com sucesso'
          });
        }
      });
  }

  excluir(row: UnidadeResponse): void {
    this.cs.confirm({
      header: 'Excluir',
      message: `Excluir unidade?`,
      rejectLabel: 'Cancelar',
      acceptLabel: 'Excluir',
      accept: () => {
        this.service.excluir(row.id).subscribe(() => this.carregar({ first: 0, rows: 10 }));
      },
    });
  }
}
