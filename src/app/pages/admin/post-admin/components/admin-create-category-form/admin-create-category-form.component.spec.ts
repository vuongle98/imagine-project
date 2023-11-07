import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCreateCategoryFormComponent } from './admin-create-category-form.component';

describe('AdminCreateCategoryFormComponent', () => {
  let component: AdminCreateCategoryFormComponent;
  let fixture: ComponentFixture<AdminCreateCategoryFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminCreateCategoryFormComponent]
    });
    fixture = TestBed.createComponent(AdminCreateCategoryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
