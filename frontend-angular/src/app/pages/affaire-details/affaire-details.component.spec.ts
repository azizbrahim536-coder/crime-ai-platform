import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AffaireDetailsComponent } from './affaire-details.component';

describe('AffaireDetailsComponent', () => {
  let component: AffaireDetailsComponent;
  let fixture: ComponentFixture<AffaireDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AffaireDetailsComponent]
    });
    fixture = TestBed.createComponent(AffaireDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
