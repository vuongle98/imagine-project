import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileAdminIndexComponent } from './file-admin-index.component';

describe('FileAdminIndexComponent', () => {
  let component: FileAdminIndexComponent;
  let fixture: ComponentFixture<FileAdminIndexComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FileAdminIndexComponent]
    });
    fixture = TestBed.createComponent(FileAdminIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
