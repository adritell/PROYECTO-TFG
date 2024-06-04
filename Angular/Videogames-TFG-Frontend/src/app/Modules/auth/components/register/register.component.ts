import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../../Services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit{

  registerForm!: FormGroup;
  passwordsDoNotMatch: boolean = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      apellidos: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9._%+-]+@(gmail\.com)$/)]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(20),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=])(?!.*[.]).{8,20}$/)
      ]],
      confirmPassword: ['', Validators.required]
    });
  }

  onSubmit(datosRegistro: any): void {
    if (this.registerForm.valid && this.registerForm.value.password === this.registerForm.value.confirmPassword) {
      this.passwordsDoNotMatch = false;
      console.log("Form is valid and passwords match");
      this.authService.registrarUsuario(this.registerForm.value).subscribe({
        next: (resp) => {
          alert('User registered successfully');
          this.router.navigate(['/auth/login']);
        },
        error: (error) => {
          console.error('Error registering user: ', error);
        }
      });
    } else {
      console.log("Form is invalid or passwords do not match");
      this.passwordsDoNotMatch = true;
    }
  }
}
