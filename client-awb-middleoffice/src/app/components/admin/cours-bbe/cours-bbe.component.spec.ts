import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoursBbeComponent } from './cours-bbe.component';

describe('CoursBbeComponent', () => {
  let component: CoursBbeComponent;
  let fixture: ComponentFixture<CoursBbeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoursBbeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoursBbeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
