import { Component } from '@angular/core';
import { LoginGuard } from './Guards/login.guard';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Videogames-TFG-Frontend';

  constructor(private loginGuard: LoginGuard) {}

  get isLoggedIn(): boolean {
    return this.loginGuard.authServiceisLoggedIn;
  }
}
