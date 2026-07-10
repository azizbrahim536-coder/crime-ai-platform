import { TestBed } from '@angular/core/testing';

import { PersonneImpliqueeService } from './personne-impliquee.service';

describe('PersonneImpliqueeService', () => {
  let service: PersonneImpliqueeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonneImpliqueeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
