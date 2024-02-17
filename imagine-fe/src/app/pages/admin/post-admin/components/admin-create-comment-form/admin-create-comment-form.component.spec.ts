import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCreateCommentFormComponent } from './admin-create-comment-form.component';

describe('AdminCreateCommentFormComponent', () => {
  let component: AdminCreateCommentFormComponent;
  let fixture: ComponentFixture<AdminCreateCommentFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminCreateCommentFormComponent]
    });
    fixture = TestBed.createComponent(AdminCreateCommentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
