import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSearchCategoryFormComponent } from './admin-search-category-form.component';

describe('AdminSearchCategoryFormComponent', () => {
  let component: AdminSearchCategoryFormComponent;
  let fixture: ComponentFixture<AdminSearchCategoryFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSearchCategoryFormComponent]
    });
    fixture = TestBed.createComponent(AdminSearchCategoryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
