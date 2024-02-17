import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSearchQuestionComponent } from './admin-search-question.component';

describe('AdminSearchQuestionComponent', () => {
  let component: AdminSearchQuestionComponent;
  let fixture: ComponentFixture<AdminSearchQuestionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSearchQuestionComponent]
    });
    fixture = TestBed.createComponent(AdminSearchQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
