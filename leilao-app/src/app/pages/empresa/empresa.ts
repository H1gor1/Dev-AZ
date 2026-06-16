import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { NgxMaskDirective } from 'ngx-mask';
import { EmpresaService } from '../../core/services/empresa.service';
import {CnpjValidator} from '../../shared/validators/cnpj.validator';

@Component({
  selector: 'app-empresa',
  imports: [ReactiveFormsModule, ButtonModule, InputTextModule, RouterLink, NgxMaskDirective],
  templateUrl: './empresa.html',
})
export class Empresa {
  private fb = inject(FormBuilder);
  private service = inject(EmpresaService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private msg = inject(MessageService);

  editando = signal(false);
  salvando = signal(false);

  form = this.fb.group({
    id: [null as number | null],
    razaoSocial: ['', [Validators.required, Validators.maxLength(64)]],
    cnpj: ['', [Validators.required, CnpjValidator.validate]],
    email: ['', [Validators.required, Validators.email]],
    usuario: ['', [Validators.required, Validators.maxLength(20)]],
    password: ['', Validators.required],
    telefone: ['', Validators.maxLength(32)],
    logradouro: ['', Validators.maxLength(64)],
    numero: [''],
    complemento: ['', Validators.maxLength(64)],
    bairro: ['', Validators.maxLength(64)],
    municipio: ['', Validators.maxLength(64)],
    cep: ['', Validators.maxLength(8)],
    site: ['', Validators.maxLength(1000)],
  });

  constructor() {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.editando.set(true);
      this.form.get('password')?.clearValidators();
      this.service.buscarPorId(+id).subscribe((res) => {
        this.form.patchValue(res.data);
        this.form.get('password')?.setValue('');
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.salvando.set(true);
    const raw = this.form.getRawValue();

    if (this.editando()) {
      const { id, password, ...req } = raw;
      this.service.atualizar(id!, { ...req } as any).subscribe({
        next: () => this.sucesso('atualizada'),
        error: () => this.salvando.set(false),
      });
    } else {
      this.service.criar(raw as any).subscribe({
        next: () => this.sucesso('criada'),
        error: () => this.salvando.set(false),
      });
    }
  }

  private sucesso(acao: string): void {
    this.msg.add({ severity: 'success', summary: 'Sucesso', detail: `Empresa ${acao} com sucesso` });
    this.router.navigate(['/empresas']);
  }
}
