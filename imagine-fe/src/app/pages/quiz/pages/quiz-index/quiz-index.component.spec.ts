import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizIndexComponent } from './quiz-index.component';

describe('QuizIndexComponent', () => {
  let component: QuizIndexComponent;
  let fixture: ComponentFixture<QuizIndexComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuizIndexComponent]
    });
    fixture = TestBed.createComponent(QuizIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
