import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [CardModule, ButtonModule, RouterLink],
  templateUrl: './dashboard.html',
})
export class Dashboard {}
