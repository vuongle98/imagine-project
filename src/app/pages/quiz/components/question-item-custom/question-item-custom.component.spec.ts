import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionItemCustomComponent } from './question-item-custom.component';

describe('QuestionItemCustomComponent', () => {
  let component: QuestionItemCustomComponent;
  let fixture: ComponentFixture<QuestionItemCustomComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuestionItemCustomComponent]
    });
    fixture = TestBed.createComponent(QuestionItemCustomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
