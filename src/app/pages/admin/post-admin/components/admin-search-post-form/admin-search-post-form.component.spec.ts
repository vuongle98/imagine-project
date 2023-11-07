import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSearchPostFormComponent } from './admin-search-post-form.component';

describe('AdminSearchPostFormComponent', () => {
  let component: AdminSearchPostFormComponent;
  let fixture: ComponentFixture<AdminSearchPostFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSearchPostFormComponent]
    });
    fixture = TestBed.createComponent(AdminSearchPostFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
