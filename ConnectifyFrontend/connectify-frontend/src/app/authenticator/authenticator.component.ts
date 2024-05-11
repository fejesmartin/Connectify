import { Component } from '@angular/core';
import { AuthService } from '../auth-service'; // Import the AuthService
import { RegisterModel } from '../models/RegisterModel';
import { LoginModel } from '../models/LoginModel';
import { Router } from '@angular/router';

@Component({
  selector: 'app-authenticator',
  templateUrl: './authenticator.component.html',
  styleUrls: ['./authenticator.component.css']
})
export class AuthenticatorComponent {
  state = AuthenticatorCompState.LOGIN;
  passwordMisMatch = false;

  constructor(private authService: AuthService, private router: Router) {}

  onForgotPasswordClick(): void {
    this.state = AuthenticatorCompState.FORGOT_PASSWORD;
  }

  onCreateAccountClick(): void {
    this.state = AuthenticatorCompState.REGISTER;
  }

  onLoginClick(): void {
    this.state = AuthenticatorCompState.LOGIN;
  }

  async onLogin(loginEmail: HTMLInputElement, loginPassword: HTMLInputElement): Promise<void> {
    const email = loginEmail.value;
    const password = loginPassword.value;

    try {
      await this.authService.login(email, password);
      alert("Login successful!");
      loginEmail.value = "";
      loginPassword.value = "";
      this.router.navigate(["postfeed"]);
    } catch (error) {
      console.error(error);
      alert('Login failed');
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  onRegisterClick(registerEmail: HTMLInputElement, registerUsername: HTMLInputElement, registerPassword: HTMLInputElement, registerConfirmPassword: HTMLInputElement): void {
    if (registerPassword.value !== registerConfirmPassword.value) {
      this.passwordMisMatch = true;
      return;
    }

    const registerData: RegisterModel = {
      username: registerUsername.value,
      email: registerEmail.value,
      password: registerPassword.value
    };

    try {
      // Call AuthService register method
      this.authService.register(registerData);
      alert("Registration successful!");
      registerEmail.value = "";
      registerUsername.value = "";
      registerPassword.value = "";
      registerConfirmPassword.value = "";
    } catch (error) {
      console.error(error);
      alert('Registration failed');
    }
  }

  isLoginState(): boolean {
    return this.state === AuthenticatorCompState.LOGIN;
  }

  isRegisterState(): boolean {
    return this.state === AuthenticatorCompState.REGISTER;
  }

  isForgotPasswordState(): boolean {
    return this.state === AuthenticatorCompState.FORGOT_PASSWORD;
  }

  getStateText(): string {
    switch (this.state) {
      case AuthenticatorCompState.LOGIN:
        return "Login";

      case AuthenticatorCompState.FORGOT_PASSWORD:
        return "Forgot Password?";

      case AuthenticatorCompState.REGISTER:
        return "Register";
    }
  }
}

export enum AuthenticatorCompState {
  LOGIN,
  REGISTER,
  FORGOT_PASSWORD
}
