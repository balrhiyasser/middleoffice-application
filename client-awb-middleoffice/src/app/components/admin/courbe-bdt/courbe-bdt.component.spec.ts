import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CourbeBdtComponent } from './courbe-bdt.component';

describe('CourbeBdtComponent', () => {
  let component: CourbeBdtComponent;
  let fixture: ComponentFixture<CourbeBdtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CourbeBdtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CourbeBdtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
