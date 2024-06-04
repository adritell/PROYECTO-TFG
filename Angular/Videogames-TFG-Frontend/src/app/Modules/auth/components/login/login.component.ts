import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../../../Services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  error: string = ''; // Variable para almacenar el mensaje de error

  loginForm = this.formBuilder.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  editar(user: any) {
    this.authService.login(user.email, user.password);
  }
}
