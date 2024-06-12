import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UsuarioAdminResponse } from '../../Interfaces/DTO/UsuarioAdminResponse';
import { UsuarioResponse } from '../../Interfaces/DTO/UsuarioResponse';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private apiUrl = 'http://localhost:8080/api/v1/usuario';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllUsuarios(): Observable<UsuarioAdminResponse[] | UsuarioResponse[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (this.authService.isAdminUser()) {
      return this.http.get<UsuarioAdminResponse[]>(this.apiUrl, { headers });
    } else {
      return this.http.get<UsuarioResponse[]>(this.apiUrl, { headers });
    }
  }

  getUsuarioById(id: number): Observable<UsuarioAdminResponse | UsuarioResponse> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (this.authService.isAdminUser()) {
      return this.http.get<UsuarioAdminResponse>(`${this.apiUrl}/${id}`, { headers });
    } else {
      return this.http.get<UsuarioResponse>(`${this.apiUrl}/${id}`, { headers });
    }
  }



  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  editarUsuario(id: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, usuario);
  }

}
