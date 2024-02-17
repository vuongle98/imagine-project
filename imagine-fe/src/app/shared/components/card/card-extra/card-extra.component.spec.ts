import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardExtraComponent } from './card-extra.component';

describe('CardExtraComponent', () => {
  let component: CardExtraComponent;
  let fixture: ComponentFixture<CardExtraComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardExtraComponent]
    });
    fixture = TestBed.createComponent(CardExtraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
