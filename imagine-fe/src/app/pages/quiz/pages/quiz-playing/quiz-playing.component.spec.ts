import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizPlayingComponent } from './quiz-playing.component';

describe('QuizPlayingComponent', () => {
  let component: QuizPlayingComponent;
  let fixture: ComponentFixture<QuizPlayingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuizPlayingComponent]
    });
    fixture = TestBed.createComponent(QuizPlayingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
