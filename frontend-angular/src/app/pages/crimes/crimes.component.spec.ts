import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrimesComponent } from './crimes.component';

describe('CrimesComponent', () => {
  let component: CrimesComponent;
  let fixture: ComponentFixture<CrimesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrimesComponent]
    });
    fixture = TestBed.createComponent(CrimesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
