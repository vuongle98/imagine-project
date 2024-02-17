import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAdminCategoryComponent } from './list-admin-category.component';

describe('ListAdminCategoryComponent', () => {
  let component: ListAdminCategoryComponent;
  let fixture: ComponentFixture<ListAdminCategoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListAdminCategoryComponent]
    });
    fixture = TestBed.createComponent(ListAdminCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
