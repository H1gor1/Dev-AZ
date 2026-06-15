import { Component, OnInit } from '@angular/core';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  imports: [MenubarModule],
  templateUrl: './navbar.html',
})
export class Navbar implements OnInit {
  items: MenuItem[] | undefined;

  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: '/',
      },
      {
        label: 'Empresas',
        icon: 'pi pi-building',
        routerLink: '/empresa',
      },
      {
        label: 'Unidades',
        icon: 'pi pi-box',
        routerLink: '/unidade',
      },
      {
        label: 'Leilões',
        icon: 'pi pi-tag',
        routerLink: '/leilao',
      },
      {
        label: 'Lotes',
        icon: 'pi pi-list',
        routerLink: '/lote',
      },
    ];
  }
}
