import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCategoryTableComponent } from './admin-category-table.component';

describe('AdminCategoryTableComponent', () => {
  let component: AdminCategoryTableComponent;
  let fixture: ComponentFixture<AdminCategoryTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminCategoryTableComponent]
    });
    fixture = TestBed.createComponent(AdminCategoryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
