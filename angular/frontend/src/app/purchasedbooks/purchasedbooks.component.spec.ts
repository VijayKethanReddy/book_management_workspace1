import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasedbooksComponent } from './purchasedbooks.component';

describe('PurchasedbooksComponent', () => {
  let component: PurchasedbooksComponent;
  let fixture: ComponentFixture<PurchasedbooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchasedbooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchasedbooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
