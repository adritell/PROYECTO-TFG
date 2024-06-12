import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { VideojuegoDTO } from '../../Interfaces/DTO/VideojuegoDTO';
import { BehaviorSubject, Observable, catchError, map, tap, throwError, toArray } from 'rxjs';
import { PaginatedResponse } from '../../Interfaces/DTO/PaginatedResponse';
import Swal from 'sweetalert2';
import { AuthService } from '../auth/auth.service';
import { ComentarioResponse } from '../../Interfaces/DTO/ComentarioResponse';

@Injectable({
  providedIn: 'root'
})
export class VideogamesService {

  private videogamesSubject: BehaviorSubject<VideojuegoDTO[]> = new BehaviorSubject<VideojuegoDTO[]>([]);
  public videogames$: Observable<VideojuegoDTO[]> = this.videogamesSubject.asObservable();

  private filteredVideogamesSubject: BehaviorSubject<VideojuegoDTO[]> = new BehaviorSubject<VideojuegoDTO[]>([]);
  public filteredVideogames$: Observable<VideojuegoDTO[]> = this.filteredVideogamesSubject.asObservable();

  private apiUrl = 'http://localhost:8080/api/v1/videojuegos';

  constructor(private http: HttpClient, private authService: AuthService) {
    this.loadVideogames();
  }

  private loadVideogames() {
    this.http.get<PaginatedResponse<VideojuegoDTO>>(this.apiUrl).pipe(
      catchError(error => {
        Swal.fire({
          icon: 'error',
          title: 'Error fetching games',
          text: error.message
        });
        return throwError(error);
      })
    ).subscribe(response => {
      if (Array.isArray(response.content)) {
        this.videogamesSubject.next(response.content);
        this.filteredVideogamesSubject.next(response.content);  // Initialize filtered games with all games
      } else {
        console.error('Expected an array of videogames but got', response);
      }
    });
  }

  getVideojuegosPaginados(page: number, size: number): Observable<PaginatedResponse<VideojuegoDTO>> {
    const url = `${this.apiUrl}?page=${page}&size=${size}`;
    return this.http.get<PaginatedResponse<VideojuegoDTO>>(url, { headers: { 'Content-Type': 'application/json' } }).pipe(
      map(response => {
        if (Array.isArray(response.content)) {
          this.videogamesSubject.next(response.content);
          this.filteredVideogamesSubject.next(response.content);  // Update filtered games with new data
        } else {
          console.error('Expected an array of videogames in response content but got', response.content);
        }
        return response;
      }),
      catchError(error => {
        Swal.fire({
          icon: 'error',
          title: 'Error fetching games',
          text: error.message
        });
        return throwError(error);
      })
    );
  }

  //Metodo para obtener todos los juegos sin paginado
  getAllGames(): Observable<VideojuegoDTO[]> {
    return this.http.get<VideojuegoDTO[]>(`${this.apiUrl}/todos`);
  }

  updateVideojuego(id: number, game: VideojuegoDTO, token: string): Observable<VideojuegoDTO> {
    return this.http.put<VideojuegoDTO>(`${this.apiUrl}/${id}`, game, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }).pipe(
      tap(() => {
        // Actualiza la lista de videojuegos después de una actualización
        this.loadVideogames();
      })
    );
  }

  deleteVideojuego(id: number, token: string): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<void>(url, { headers }).pipe(
      catchError(error => {
        Swal.fire({
          icon: 'error',
          title: 'Error deleting game',
          text: error.message
        });
        return throwError(error);
      })
    );
  }

  getGameById(id: number): Observable<VideojuegoDTO> {
    return this.http.get<VideojuegoDTO>(`${this.apiUrl}/${id}`);
  }


  guardarVideojuego(game: VideojuegoDTO, token: string): Observable<VideojuegoDTO> {
    return this.http.post<VideojuegoDTO>(this.apiUrl, game, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).pipe(
      tap((nuevoJuego) => {
        // Añade el nuevo videojuego a la lista actual
        const currentGames = this.videogamesSubject.value;
        this.videogamesSubject.next([...currentGames, nuevoJuego]);
      }),
      catchError(error => {
        Swal.fire({
          icon: 'error',
          title: 'Error adding game',
          text: error.message
        });
        return throwError(error);
      })
    );
  }


  //Manejar juegos favoritos
  getFavorites(userId: number): Observable<VideojuegoDTO[]> {
    return this.http.get<VideojuegoDTO[]>(`http://localhost:8080/api/v1/usuario/${userId}/videojuegos-favoritos`);
  }

  addFavorite(userId: number, gameId: number): Observable<void> {
    return this.http.post<void>(`http://localhost:8080/api/v1/usuario/${userId}/videojuegos-favoritos/${gameId}`, {});
  }

  removeFavorite(userId: number, gameId: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/api/v1/usuario/${userId}/videojuegos-favoritos/${gameId}`);
  }



  //Manejar comentarios de juegos
  getComentariosByVideojuego(id: number): Observable<ComentarioResponse[]> {
    return this.http.get<ComentarioResponse[]>(`${this.apiUrl}/${id}/comentarios`);
  }

}
  /*filterData(valueToSearch: string): void {
    const filteredGames = this.videogamesSubject.value.filter((game) => {
      return game.nombre.toLowerCase().includes(valueToSearch.toLowerCase());
    });
    this.filteredVideogamesSubject.next(filteredGames);
  }*/
// }
