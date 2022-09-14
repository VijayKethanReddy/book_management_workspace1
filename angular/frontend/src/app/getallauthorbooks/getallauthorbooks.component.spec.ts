import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetallauthorbooksComponent } from './getallauthorbooks.component';

describe('GetallauthorbooksComponent', () => {
  let component: GetallauthorbooksComponent;
  let fixture: ComponentFixture<GetallauthorbooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetallauthorbooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetallauthorbooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
