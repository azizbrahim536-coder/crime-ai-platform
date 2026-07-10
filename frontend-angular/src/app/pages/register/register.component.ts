import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../models/register-request';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerData: RegisterRequest = {
    nom: '',
    prenom: '',
    email: '',
    password: '',
    telephone: '',
    roleName: 'ENQUETEUR'
  };

  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  register(): void {
    if (
      !this.registerData.nom ||
      !this.registerData.prenom ||
      !this.registerData.email ||
      !this.registerData.password
    ) {
      this.errorMessage = 'Veuillez remplir les champs obligatoires';
      return;
    }

    this.authService.register(this.registerData).subscribe({
      next: (response) => {
        this.authService.saveAuthData(response);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Erreur register', err);
        this.errorMessage = 'Erreur lors de la création du compte';
      }
    });
  }
}
