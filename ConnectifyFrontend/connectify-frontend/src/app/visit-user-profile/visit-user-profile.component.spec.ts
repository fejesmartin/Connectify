import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitUserProfileComponent } from './visit-user-profile.component';

describe('VisitUserProfileComponent', () => {
  let component: VisitUserProfileComponent;
  let fixture: ComponentFixture<VisitUserProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisitUserProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VisitUserProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
