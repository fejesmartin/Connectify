import { Component } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { AuthenticatorComponent } from './authenticator/authenticator.component';
import { AuthService } from './auth-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'connectify-frontend';
  showDropdown = false;

  constructor(private loginSheet: MatBottomSheet, private authService: AuthService){

  }

  onLoginClick(){
    this.loginSheet.open(AuthenticatorComponent);
  }

  isAuthenticated():boolean{
    return this.authService.isAuthenticated();
  }
  
  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }

  getUsername(): string {
    return this.authService.getUsername();
  }

  logout(): void {
    this.authService.logout();
  }
}
