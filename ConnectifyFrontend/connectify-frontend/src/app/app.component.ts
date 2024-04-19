import { Component } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { AuthenticatorComponent } from './authenticator/authenticator.component';
import { AuthService } from './auth-service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'] // Use styleUrls instead of styleUrl
})
export class AppComponent {
  title = 'connectify-frontend';
  showDropdown = false;
  username: string = ""; 

  constructor(private loginSheet: MatBottomSheet, private authService: AuthService, private cookieService: CookieService){
    this.username = cookieService.get("username");
  }

  onLoginClick(){
    this.loginSheet.open(AuthenticatorComponent);
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }
  
  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }

  logout(): void {
    this.authService.logout();
  }
}
