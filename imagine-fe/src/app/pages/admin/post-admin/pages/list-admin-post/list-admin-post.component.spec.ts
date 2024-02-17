import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAdminPostComponent } from './list-admin-post.component';

describe('ListAdminPostComponent', () => {
  let component: ListAdminPostComponent;
  let fixture: ComponentFixture<ListAdminPostComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListAdminPostComponent]
    });
    fixture = TestBed.createComponent(ListAdminPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
