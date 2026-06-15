import { Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Empresa } from './pages/empresa/empresa';
import { Unidades } from './pages/unidades/unidades';
import { Leiloes } from './pages/leiloes/leiloes';
import { Lotes } from './pages/lotes/lotes';

export const routes: Routes = [
  { path: '', component: Dashboard },
  { path: 'empresa', component: Empresa },
  { path: 'unidade', component: Unidades },
  { path: 'leilao', component: Leiloes },
  { path: 'lote', component: Lotes },
  { path: '**', redirectTo: '' },
];
