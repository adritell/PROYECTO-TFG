import { Component, OnInit } from '@angular/core';
import { LoginGuard } from './Guards/login.guard';
import { AuthService } from './Services/auth/auth.service';
import { User } from './Interfaces/user';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'Videogames';
  currentUser: User | null = null;
  isAdmin: Boolean = false;
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Suscribirse a los cambios en el estado de autenticación
    this.authService.currentUser.subscribe((user: User | null) => {
      this.currentUser = user;
    });
  }
}
