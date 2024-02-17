import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionSelectComponent } from './question-select.component';

describe('QuestionSelectComponent', () => {
  let component: QuestionSelectComponent;
  let fixture: ComponentFixture<QuestionSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuestionSelectComponent]
    });
    fixture = TestBed.createComponent(QuestionSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
