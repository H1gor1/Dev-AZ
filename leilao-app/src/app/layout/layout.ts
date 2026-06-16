import { Component } from '@angular/core';
import {Navbar} from './navbar/navbar';
import {RouterOutlet} from '@angular/router';
import {Toast} from 'primeng/toast';
import {ConfirmDialogModule} from 'primeng/confirmdialog';

@Component({
  selector: 'app-layout',
  imports: [
    Navbar,
    RouterOutlet,
    Toast,
    ConfirmDialogModule,
  ],
  templateUrl: './layout.html',
})
export class Layout {}
