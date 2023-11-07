import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCreatePostFormComponent } from './admin-create-post-form.component';

describe('AdminCreatePostFormComponent', () => {
  let component: AdminCreatePostFormComponent;
  let fixture: ComponentFixture<AdminCreatePostFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminCreatePostFormComponent]
    });
    fixture = TestBed.createComponent(AdminCreatePostFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
