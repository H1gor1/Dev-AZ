import { Component, inject, signal } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { Select } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { forkJoin } from 'rxjs';
import { LeilaoService } from '../../core/services/leilao.service';
import { LoteService } from '../../core/services/lote.service';
import { EmpresaService } from '../../core/services/empresa.service';
import { UnidadeService } from '../../core/services/unidade.service';
import { EmpresaResponse } from '../../core/models/empresa.model';
import { UnidadeResponse } from '../../core/models/unidade.model';

@Component({
  selector: 'app-leilao',
  imports: [ReactiveFormsModule, ButtonModule, InputTextModule, Select, TableModule, RouterLink],
  templateUrl: './leilao.html',
})
export class Leilao {
  private fb = inject(FormBuilder);
  private service = inject(LeilaoService);
  private loteService = inject(LoteService);
  private empresaService = inject(EmpresaService);
  private unidadeService = inject(UnidadeService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private msg = inject(MessageService);

  editando = signal(false);
  salvando = signal(false);
  leilaoId: number | null = null;

  empresas = signal<EmpresaResponse[]>([]);
  unidades = signal<UnidadeResponse[]>([]);

  form = this.fb.group({
    codigo: [0, Validators.required],
    descricao: ['', [Validators.required, Validators.maxLength(60)]],
    vendedorId: [null as number | null, Validators.required],
    inicioPrevisto: ['', Validators.required],
    lotes: this.fb.array<FormGroup>([]),
    lotesRemovidos: this.fb.array<number>([]),
  });

  get lotesArray(): FormArray<FormGroup> {
    return this.form.get('lotes') as FormArray<FormGroup>;
  }

  constructor() {
    this.carregarEmpresas();
    this.carregarUnidades();

    const id = this.route.snapshot.params['id'];
    if (id) {
      this.editando.set(true);
      this.leilaoId = +id;
      this.carregarLeilao(+id);
    }
  }

  private carregarEmpresas(): void {
    this.empresaService.listar(0, 999).subscribe((res) => this.empresas.set(res.data));
  }

  private carregarUnidades(): void {
    this.unidadeService.listar(0, 999).subscribe((res) => this.unidades.set(res.data));
  }

  private carregarLeilao(id: number): void {
    forkJoin({
      leilao: this.service.buscarPorId(id),
      lotes: this.loteService.listar(0, 999, id),
    }).subscribe(({ leilao, lotes }) => {
      const l = leilao.data;
      this.form.patchValue({
        codigo: l.codigo,
        descricao: l.descricao,
        vendedorId: l.vendedor.id,
        inicioPrevisto: this.formatDateForInput(l.inicioPrevisto),
      });

      lotes.data.forEach((lo) => {
        this.adicionarLote({
          id: lo.id,
          numeroLote: lo.numeroLote,
          descricao: lo.descricao,
          quantidade: lo.quantidade,
          valorInicial: lo.valorInicial,
          unidadeId: lo.unidade.id,
        });
      });
    });
  }

  criarLoteGroup(dados?: {
    id?: number;
    numeroLote?: number;
    descricao?: string;
    quantidade?: number;
    valorInicial?: number;
    unidadeId?: number;
  }): FormGroup {
    return this.fb.group({
      id: [dados?.id ?? null],
      numeroLote: [dados?.numeroLote ?? 0, Validators.required],
      descricao: [dados?.descricao ?? '', [Validators.required, Validators.maxLength(60)]],
      quantidade: [dados?.quantidade ?? 0, [Validators.required, Validators.min(0.01)]],
      valorInicial: [dados?.valorInicial ?? 0, [Validators.required, Validators.min(0)]],
      unidadeId: [dados?.unidadeId ?? null, Validators.required],
    });
  }

  adicionarLote(dados?: {
    id?: number;
    numeroLote?: number;
    descricao?: string;
    quantidade?: number;
    valorInicial?: number;
    unidadeId?: number;
  }): void {
    this.lotesArray.push(this.criarLoteGroup(dados));
  }

  removerLote(index: number): void {
    const grupo = this.lotesArray.at(index);
    const id = grupo.get('id')?.value as number | null;
    if (id) {
      (this.form.get('lotesRemovidos') as FormArray).push(this.fb.control(id));
    }
    this.lotesArray.removeAt(index);
  }

  asFormGroup(grupo: any): FormGroup {
    return grupo as FormGroup;
  }

  private formatDateForInput(dateStr: string): string {
    const d = new Date(dateStr);
    const pad = (n: number) => n.toString().padStart(2, '0');
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.salvando.set(true);
    const raw = this.form.getRawValue();

    if (this.editando()) {
      const { lotes, lotesRemovidos, vendedorId: _v, ...leilaoData } = raw;
      this.service.atualizar(this.leilaoId!, {
        codigo: leilaoData.codigo ?? undefined,
        descricao: leilaoData.descricao ?? undefined,
        inicioPrevisto: leilaoData.inicioPrevisto ?? undefined,
      }).subscribe({
        next: () => this.salvarLotes(raw.lotes, (raw.lotesRemovidos ?? []).filter((id): id is number => id != null)),
        error: () => this.salvando.set(false),
      });
    } else {
      const { lotes, lotesRemovidos: _r, ...leilaoData } = raw;
      this.service.criar({
        codigo: leilaoData.codigo ?? 0,
        descricao: leilaoData.descricao ?? '',
        vendedorId: leilaoData.vendedorId ?? 0,
        inicioPrevisto: leilaoData.inicioPrevisto ?? '',
      }).subscribe({
        next: (res) => {
          this.leilaoId = res.data.id;
          this.salvarLotes(raw.lotes ?? [], []);
        },
        error: () => this.salvando.set(false),
      });
    }
  }

  private salvarLotes(lotes: any[], lotesRemovidos: number[]): void {
    const ops: any[] = [];

    (lotesRemovidos ?? []).filter((id): id is number => id != null).forEach((id) => {
      ops.push(this.loteService.excluir(id));
    });

    (lotes ?? []).forEach((lo: any) => {
      if (lo.id) {
        ops.push(
          this.loteService.atualizar(lo.id, {
            numeroLote: lo.numeroLote ?? 0,
            descricao: lo.descricao ?? '',
            quantidade: lo.quantidade ?? 0,
            valorInicial: lo.valorInicial ?? 0,
            unidadeId: lo.unidadeId ?? 0,
          }),
        );
      } else {
        ops.push(
          this.loteService.criar({
            numeroLote: lo.numeroLote ?? 0,
            descricao: lo.descricao ?? '',
            quantidade: lo.quantidade ?? 0,
            valorInicial: lo.valorInicial ?? 0,
            unidadeId: lo.unidadeId ?? 0,
            leilaoId: this.leilaoId!,
          }),
        );
      }
    });

    if (ops.length === 0) {
      this.sucesso();
      return;
    }

    forkJoin(ops).subscribe({
      next: () => this.sucesso(),
      error: () => this.salvando.set(false),
    });
  }

  private sucesso(): void {
    this.msg.add({
      severity: 'success',
      summary: 'Sucesso',
      detail: `Leilão ${this.editando() ? 'atualizado' : 'criado'} com sucesso`,
    });
    this.router.navigate(['/leiloes']);
  }
}
