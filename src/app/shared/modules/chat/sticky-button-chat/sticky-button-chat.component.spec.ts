import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StickyButtonChatComponent } from './sticky-button-chat.component';

describe('StickyButtonChatComponent', () => {
  let component: StickyButtonChatComponent;
  let fixture: ComponentFixture<StickyButtonChatComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StickyButtonChatComponent]
    });
    fixture = TestBed.createComponent(StickyButtonChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
