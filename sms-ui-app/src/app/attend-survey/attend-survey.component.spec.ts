import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendSurveyComponent } from './attend-survey.component';

describe('AttendSurveyComponent', () => {
  let component: AttendSurveyComponent;
  let fixture: ComponentFixture<AttendSurveyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendSurveyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendSurveyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
