import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileAdminCreateFormComponent } from './file-admin-create-form.component';

describe('FileAdminCreateFormComponent', () => {
  let component: FileAdminCreateFormComponent;
  let fixture: ComponentFixture<FileAdminCreateFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FileAdminCreateFormComponent]
    });
    fixture = TestBed.createComponent(FileAdminCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
