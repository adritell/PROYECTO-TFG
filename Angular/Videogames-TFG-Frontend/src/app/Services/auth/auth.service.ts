import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../../Interfaces/user';
import { TokenPayload } from '../../Interfaces/tokenPayload';
import { TokenResponse } from '../../Interfaces/tokenResponse';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';

  currentUser: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  isAdmin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  id: number | null = null;

  constructor(private http: HttpClient, private router: Router) {
    this.initializeCurrentUser();
  }

  // Método para iniciar sesión y guardar el token en el localStorage
  login(email: string, password: string): void {
    this.http
      .post<TokenResponse>(`${this.apiUrl}/signin`, { email, password })
      .subscribe({
        next: (resp) => {
          this.decodeToken(resp.token);
          localStorage.setItem('token', resp.token);
          this.router.navigate(['']);
        },
        error: (resp) => {
          console.error(resp);
          alert(resp.message);
        },
      });
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

 // Método para cerrar sesión y eliminar el token del localStorage
 logout(): void {
  localStorage.removeItem('token');
  this.clearCurrentUser();
  this.router.navigate(['/auth/login']);
}

// Método para registrar un nuevo usuario
registrarUsuario(datosRegistro: any): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/signup`, datosRegistro);
}

  private initializeCurrentUser(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.decodeToken(token);
    } else {
      this.clearCurrentUser();
    }
  }

  private clearCurrentUser(): void {
    this.currentUser.next(null);
    this.isAdmin.next(false);
  }

  decodeToken(token: string): void {
    const tokenDecoded = jwtDecode<TokenPayload>(token);
    if (tokenDecoded) {
      console.log(tokenDecoded.id);
      const user: User = {
        id: tokenDecoded.id,
        roles: tokenDecoded.roles,
        expiration: tokenDecoded.expiration,
        nombreUsuario: tokenDecoded.nombreUsuario,
        sub: tokenDecoded.sub
      };
      this.currentUser.next(user);
      this.isAdmin.next(user.roles.includes('ROLE_ADMIN'));
      this.id = tokenDecoded.id;
    } else {
      this.clearCurrentUser();
    }
  }


  //Metodo para comprobar si el usuario es administrador o no
  isAdminUser(): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      const tokenDecoded = jwtDecode<TokenPayload>(token);
      return tokenDecoded.roles.includes('ROLE_ADMIN');
    }
    return false;
  }

  //Metodo para obtener el id del usuario actual
  getCurrentUserId(): number | null {
    return this.id;
  }

}
