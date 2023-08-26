import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoBaseLayoutComponent } from './no-base-layout.component';

describe('NoBaseLayoutComponent', () => {
  let component: NoBaseLayoutComponent;
  let fixture: ComponentFixture<NoBaseLayoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NoBaseLayoutComponent]
    });
    fixture = TestBed.createComponent(NoBaseLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
