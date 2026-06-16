import { Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Empresa } from './pages/empresa/empresa';
import { Empresas } from './pages/empresas/empresas';
import { Unidades } from './pages/unidades/unidades';
import { Leiloes } from './pages/leiloes/leiloes';
import { Leilao } from './pages/leilao/leilao';

export const routes: Routes = [
  { path: '', component: Dashboard },
  { path: 'empresas', component: Empresas },
  { path: 'empresa', component: Empresa },
  { path: 'empresa/:id', component: Empresa },
  { path: 'unidades', component: Unidades },
  { path: 'leiloes', component: Leiloes },
  { path: 'leilao', component: Leilao },
  { path: 'leilao/:id', component: Leilao },
  { path: '**', redirectTo: '' },
];
