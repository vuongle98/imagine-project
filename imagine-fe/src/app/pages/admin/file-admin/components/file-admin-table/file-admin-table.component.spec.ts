import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileAdminTableComponent } from './file-admin-table.component';

describe('FileAdminTableComponent', () => {
  let component: FileAdminTableComponent;
  let fixture: ComponentFixture<FileAdminTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FileAdminTableComponent]
    });
    fixture = TestBed.createComponent(FileAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
