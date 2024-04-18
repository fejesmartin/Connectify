import { Component } from '@angular/core';
import { User } from '../models/User';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { AuthenticatorComponent } from '../authenticator/authenticator.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  data: User[] = [];

  constructor(private loginSheet: MatBottomSheet) {}

  onGetStartedClick(){
    this.loginSheet.open(AuthenticatorComponent);
  }
}
