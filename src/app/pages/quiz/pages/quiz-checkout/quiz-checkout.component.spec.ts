import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizCheckoutComponent } from './quiz-checkout.component';

describe('QuizCheckoutComponent', () => {
  let component: QuizCheckoutComponent;
  let fixture: ComponentFixture<QuizCheckoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuizCheckoutComponent]
    });
    fixture = TestBed.createComponent(QuizCheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
