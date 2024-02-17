import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListQuizComponent } from './list-quiz.component';

describe('ListQuizComponent', () => {
  let component: ListQuizComponent;
  let fixture: ComponentFixture<ListQuizComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListQuizComponent]
    });
    fixture = TestBed.createComponent(ListQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
