import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/login-request';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData: LoginRequest = {
    email: '',
    password: ''
  };

  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login(): void {
    if (!this.loginData.email || !this.loginData.password) {
      this.errorMessage = 'Veuillez remplir email et mot de passe';
      return;
    }

    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        this.authService.saveAuthData(response);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Erreur login', err);
        this.errorMessage = 'Email ou mot de passe incorrect';
      }
    });
  }
}
