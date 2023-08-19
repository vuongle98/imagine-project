import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseChatComponent } from './base-chat.component';

describe('BaseChatComponent', () => {
  let component: BaseChatComponent;
  let fixture: ComponentFixture<BaseChatComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BaseChatComponent]
    });
    fixture = TestBed.createComponent(BaseChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
