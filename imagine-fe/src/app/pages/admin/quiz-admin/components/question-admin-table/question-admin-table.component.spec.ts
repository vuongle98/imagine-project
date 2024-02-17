import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionAdminTableComponent } from './question-admin-table.component';

describe('QuestionAdminTableComponent', () => {
  let component: QuestionAdminTableComponent;
  let fixture: ComponentFixture<QuestionAdminTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuestionAdminTableComponent]
    });
    fixture = TestBed.createComponent(QuestionAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
