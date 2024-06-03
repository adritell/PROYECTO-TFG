import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LoginGuard {
  constructor( private router: Router) {}

  authServiceisLoggedIn:boolean=true;

  canActivate(): boolean {
    if (this.authServiceisLoggedIn) {
      return true;

    } else {
      this.router.navigate(['/auth/login']);
      return false;
    }
  }
}












/*
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../Services/auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class LoginGuard {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['/auth/login']);
      return false;
    }
  }
}
*/
