import { Component, OnInit } from '@angular/core';
import { User } from '../../Interfaces/user';
import { AuthService } from '../../Services/auth/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {

  currentUser: User | null = null;
  isAdmin: boolean = false; // Valor temporal para isAdmin

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Suscripción para el usuario actual
    this.authService.currentUser.subscribe({
      next: (user: User | null) => {
        this.currentUser = user;
      },
      error: (err: any) => {
        console.error('Error al obtener el usuario', err);
        this.currentUser = null;
      },
    });

    // Obtener el token del almacenamiento local
    const token = localStorage.getItem('token');
    if (token) {
      this.authService.decodeToken(token);
    }

    // Suscripción para determinar si el usuario es administrador
    this.authService.isAdmin.subscribe({
      next: (resp) => {
        this.isAdmin = resp;
      },
      error: (resp) => {
        console.log(resp);
      },
    });
  }

  logout(): void {
    this.authService.logout();
  }

}
