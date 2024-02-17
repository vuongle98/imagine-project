import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPostTableComponent } from './admin-post-table.component';

describe('AdminPostTableComponent', () => {
  let component: AdminPostTableComponent;
  let fixture: ComponentFixture<AdminPostTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminPostTableComponent]
    });
    fixture = TestBed.createComponent(AdminPostTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
