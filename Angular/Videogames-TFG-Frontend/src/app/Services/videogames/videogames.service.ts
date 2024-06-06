import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { VideojuegoDTO } from '../../Interfaces/DTO/VideojuegoDTO';
import { Observable } from 'rxjs';
import { PaginatedResponse } from '../../Interfaces/DTO/PaginatedResponse';

@Injectable({
  providedIn: 'root'
})
export class VideogamesService {

  private apiUrl = 'http://localhost:8080/api/v1/videojuegos'; // Asegúrate de que esta URL apunte a tu backend

  constructor(private http: HttpClient) {}

  getVideojuegosPaginados(page: number, size: number): Observable<PaginatedResponse<VideojuegoDTO>> {
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<PaginatedResponse<VideojuegoDTO>>(`${this.apiUrl}`, { params });
  }
}
