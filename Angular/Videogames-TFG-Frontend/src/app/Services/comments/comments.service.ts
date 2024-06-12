import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ComentarioRequest } from '../../Interfaces/DTO/ComentarioRequest';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private baseUrl = 'http://localhost:8080/api/v1/comentarios'; // Cambia esta URL según tu configuración

  constructor(private http: HttpClient) { }

  addComentario(comentarioRequest: ComentarioRequest, token: string): Observable<void> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.post<void>(this.baseUrl, comentarioRequest, { headers });
  }

  updateComentario(id: number, comentarioRequest: ComentarioRequest): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, comentarioRequest);
  }

  deleteComentario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
