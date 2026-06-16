import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { MenuModule } from 'primeng/menu';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';

export interface Column {
  field: string;
  header: string;
  editable?: boolean;
  maxLength?: number;
  format?: (value: any) => string;
}

export interface Action<T = any> {
  icon: string;
  label: string;
  severity?: 'secondary' | 'success' | 'info' | 'warn' | 'danger' | 'help';
  onClick: (row: T) => void;
}

export interface CellEditEvent<T> {
  rowData: T;
  field: string;
  value: any;
}

@Component({
  selector: 'app-data-table',
  imports: [TableModule, FormsModule, InputTextModule, ButtonModule, MenuModule],
  templateUrl: './data-table.html',
})
export class DataTable<T> {
  @Input() items: T[] = [];
  @Input() cols: Column[] = [];
  @Input() actions: Action<T>[] = [];
  @Input() loading: boolean = false;
  @Input() rows: number = 10;
  @Input() totalRecords: number = 0;

  @Output() onLazyLoad = new EventEmitter<TableLazyLoadEvent>();
  @Output() onEdit = new EventEmitter<CellEditEvent<T>>();

  hasEditable(): boolean {
    return this.cols.some((c) => c.editable);
  }

  toggleMenu(menu: any, row: T, event: Event): void {
    menu.model = this.actions.map((a) => ({
      icon: 'pi pi-' + a.icon,
      label: a.label,
      command: () => a.onClick(row),
    }));
    menu.toggle(event);
  }

  cellValue(col: Column, row: T): string {
    const val = (row as any)[col.field];
    return col.format ? col.format(val) : val;
  }
}
