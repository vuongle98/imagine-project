import {
  AfterContentInit,
  Component,
  ContentChild,
  ContentChildren,
  EventEmitter,
  Input,
  Output,
  QueryList,
  ViewChild,
} from '@angular/core';
import { ColumnDirective } from './components/column.directive';

export type TableData = {
  columns: TableColumn[];
  actions: TableAction[];
  page?: number;
  size?: number;
  total?: number;
};

export type TableColumn = {
  title: string;
  dataProperty: string;
  sortable?: boolean;
  filterable?: boolean;
};

export type TableAction = {
  icon: string;
  title: string;
  action: (item: any) => void;
};

export type Dictionary = {
  [key: string]: any;
};

export enum DataType {
  TEXT = 1,
  NUMBER = 2,
  DATE = 3,
  IMAGE = 4,
  VIDEO = 5,
  ACTIONS = 6,
}

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent {
  @Input() rows: Dictionary[] = [];
  @Input() actions: TableAction[] = [];
  @Input() page = 0;
  @Input() pageSize = 10;
  @Input() totalRows = 0;
  @Output() pageChange = new EventEmitter<number>();

  @ContentChildren(ColumnDirective) columns!: QueryList<ColumnDirective>;

  DataType = DataType;

  ngOnInit(): void {}
}
