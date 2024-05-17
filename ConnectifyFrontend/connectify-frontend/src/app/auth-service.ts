import { Injectable } from '@angular/core';
import { AxiosService } from './axios.service';
import { baseURL } from '../assets/baseURL';
import { RegisterModel } from './models/RegisterModel';
import { LoginModel } from './models/LoginModel';
import { CookieService } from 'ngx-cookie-service';
import { User } from './models/User';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private axiosService: AxiosService, private cookieService: CookieService, private router: Router) {}

  id: number = 0;
  token: string = "";
  username: string = "";
  email: string = "";

  async login(email: string, password: string): Promise<void> {
    const loginData: LoginModel = { email, password };
    this.email = email;

    try {
      const response = await this.axiosService.request('post', baseURL + '/api/auth/login', loginData);
      this.token = response.data.jwt;
      this.id = response.data.id;

      // Store the token and username in cookies
      this.cookieService.set('auth_token', this.token);
      this.cookieService.set('username', response.data.username);
      this.cookieService.set('user_id', response.data.id)

      // Set the username locally
      this.username = response.data.username;
    } catch (error) {
      throw new Error('Login failed');
    }
  }

  getUsername(): string {
    // Retrieve the username from the local variable
    return this.username;
  }

  getToken(): string {
    return this.token;
  }

  getId(): number {
    return this.id;
  }

  logout(): void {
    // Clear the stored token and username
    this.cookieService.delete('auth_token');
    this.cookieService.delete('username');
    this.cookieService.delete('user_id');
    this.token = "";
    this.username = "";
    this.id = 0;
    this.router.navigate([""]);
  }

  isAuthenticated(): boolean {
    return this.cookieService.check('auth_token');
  }

  register(data: RegisterModel): void {
    try {
      this.axiosService.request('post', baseURL + '/api/auth/register', data)
        .then(response => console.log('Registration successful:', response.data));
    } catch (error) {
      throw new Error('Registration failed');
    }
  }
}
