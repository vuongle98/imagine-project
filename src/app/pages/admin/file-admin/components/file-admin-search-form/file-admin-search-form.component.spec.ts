import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileAdminSearchFormComponent } from './file-admin-search-form.component';

describe('FileAdminSearchFormComponent', () => {
  let component: FileAdminSearchFormComponent;
  let fixture: ComponentFixture<FileAdminSearchFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FileAdminSearchFormComponent]
    });
    fixture = TestBed.createComponent(FileAdminSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
