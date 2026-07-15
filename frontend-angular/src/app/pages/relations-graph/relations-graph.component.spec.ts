import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelationsGraphComponent } from './relations-graph.component';

describe('RelationsGraphComponent', () => {
  let component: RelationsGraphComponent;
  let fixture: ComponentFixture<RelationsGraphComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelationsGraphComponent]
    });
    fixture = TestBed.createComponent(RelationsGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
