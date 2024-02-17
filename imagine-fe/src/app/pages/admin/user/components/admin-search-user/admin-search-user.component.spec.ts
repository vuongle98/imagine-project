import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSearchUserComponent } from './admin-search-user.component';

describe('AdminSearchUserComponent', () => {
  let component: AdminSearchUserComponent;
  let fixture: ComponentFixture<AdminSearchUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSearchUserComponent]
    });
    fixture = TestBed.createComponent(AdminSearchUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
