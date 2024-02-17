import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSearchQuizComponent } from './admin-search-quiz.component';

describe('AdminSearchQuizComponent', () => {
  let component: AdminSearchQuizComponent;
  let fixture: ComponentFixture<AdminSearchQuizComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminSearchQuizComponent]
    });
    fixture = TestBed.createComponent(AdminSearchQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
