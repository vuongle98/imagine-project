import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizAdminTableComponent } from './quiz-admin-table.component';

describe('QuizAdminTableComponent', () => {
  let component: QuizAdminTableComponent;
  let fixture: ComponentFixture<QuizAdminTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuizAdminTableComponent]
    });
    fixture = TestBed.createComponent(QuizAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
