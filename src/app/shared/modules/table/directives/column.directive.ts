import { ContentChild, ContentChildren, Directive, Input } from '@angular/core';
import { DataType } from '../table.component';
import { CellDirective } from './cell.directive';
import { HeaderDirective } from './header.directive';

@Directive({
  selector: 'table-column',
})
export class ColumnDirective {
  @Input() header = '';
  @Input() key = '';
  @Input() renderKey = '';
  @Input() dataType = DataType.TEXT;

  @ContentChild(CellDirective, { static: true }) tplCell!: CellDirective;
  @ContentChild(HeaderDirective, { static: true }) tplHeader!: HeaderDirective;

  constructor() {}
}
