import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TodaysSpecialsComponent } from './todays-specials.component';

describe('TodaysSpecialsComponent', () => {
  let component: TodaysSpecialsComponent;
  let fixture: ComponentFixture<TodaysSpecialsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TodaysSpecialsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TodaysSpecialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
