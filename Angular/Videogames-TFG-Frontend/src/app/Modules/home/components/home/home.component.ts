import { Component, OnInit } from '@angular/core';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { AuthService } from '../../../../Services/auth/auth.service';
import { BehaviorSubject, Observable, catchError, map, tap, throwError } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

declare var bootstrap: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  currentPage: number = 0;
  pageSize: number = 6;
  totalElements: number = 0;
  totalPages: number = 0;
  editGameForm!: FormGroup;
  addGameForm!: FormGroup;
  currentGameId: number | null = null;
  videogames$: Observable<VideojuegoDTO[]>;
  favoritos: BehaviorSubject<VideojuegoDTO[]> = new BehaviorSubject<VideojuegoDTO[]>([]);

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private videogamesService: VideogamesService,
    private authService: AuthService
  ) {
    this.videogames$ = this.videogamesService.filteredVideogames$;
  }

  ngOnInit(): void {
    this.loadVideogames();
    this.loadFavorites();

    this.addGameForm = this.fb.group({
      nombre: ['', Validators.required],
      genero: ['', Validators.required],
      descripcion: ['', Validators.required],
      anioPublicacion: [0, Validators.required],
      precio: [0, Validators.required],
      calificacionPorEdades: ['', Validators.required],
      publicador: ['', Validators.required],
      imagePath: ['', Validators.required]
    });

    this.editGameForm = this.fb.group({
      nombre: ['', Validators.required],
      genero: ['', Validators.required],
      descripcion: ['', Validators.required],
      anioPublicacion: [0, Validators.required],
      precio: [0, Validators.required],
      calificacionPorEdades: ['', Validators.required],
      publicador: ['', Validators.required],
      imagePath: ['', Validators.required]
    });
  }

  loadVideogames(): void {
    this.videogames$ = this.videogamesService.getVideojuegosActivosPaginados(this.currentPage, this.pageSize).pipe(
  tap(response => {
    this.totalElements = response.totalElements;
    this.totalPages = response.totalPages;
  }),
  map(response => {
    if (Array.isArray(response.content)) {
      console.log(response.content);
      return response.content;
    } else {
      console.error('Expected an array of videogames in response content but got', response.content);
      return [];
    }
  }),
  catchError(error => {
    Swal.fire({
      icon: 'error',
      title: 'Error fetching games',
      text: error.message || 'Unknown error'
    });
    return throwError(() => error);
  })
);
  }

  nextPage(): void {
    if ((this.currentPage + 1) * this.pageSize < this.totalElements) {
      this.currentPage++;
      this.loadVideogames();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadVideogames();
    }
  }

  getDisplayedPages(): number[] {
    const totalPagesToShow = 5;
    const pages: number[] = [];

    if (this.totalPages <= totalPagesToShow) {
      for (let i = 0; i < this.totalPages; i++) pages.push(i);
    } else {
      pages.push(0);
      if (this.currentPage > 2) pages.push(-1);
      const startPage = Math.max(1, this.currentPage - 1);
      const endPage = Math.min(this.totalPages - 2, this.currentPage + 1);
      for (let i = startPage; i <= endPage; i++) pages.push(i);
      if (this.currentPage < this.totalPages - 3) pages.push(-1);
      pages.push(this.totalPages - 1);
    }

    return pages;
  }

  goToPage(page: number): void {
    this.currentPage = page;
    this.loadVideogames();
  }

  navigateToGame(game: VideojuegoDTO): void {
    this.router.navigate(['/detalles-juego', game.id]);
  }

  isAdmin(): boolean {
    return this.authService.isAdminUser();
  }

  deleteGame(gameId: number): void {
    const token = this.authService.getToken();
    this.videogamesService.deleteVideojuego(gameId, token).subscribe(
      () => {
        console.log('Game deleted successfully');
        this.loadVideogames();
      },
      error => {
        console.error('Error deleting game', error);
        Swal.fire('Error', 'No se pudo eliminar el videojuego', 'error');
      }
    );
  }

  editarVideojuego(id: number): void {
    this.currentGameId = id;
    this.videogamesService.getGameById(id).subscribe(
      game => {
        if (game) {
          this.editGameForm.patchValue({
            nombre: game.nombre,
            genero: game.genero,
            descripcion: game.descripcion,
            anioPublicacion: game.anioPublicacion,
            precio: game.precio,
            calificacionPorEdades: game.calificacionPorEdades,
            publicador: game.publicador,
            imagePath: game.imagePath
          });
          const toastElement = document.getElementById('editGameToast');
          if (toastElement) {
            const toast = new bootstrap.Toast(toastElement);
            toast.show();
          }
        }
      },
      error => {
        console.error('Error fetching the game', error);
        Swal.fire('Error', 'No se pudo cargar el videojuego', 'error');
      }
    );
  }

  onSubmitGame(): void {
    if (this.editGameForm.valid) {
      const updatedGame = this.editGameForm.value;
      if (this.currentGameId !== null) {
        this.videogamesService.updateVideojuego(this.currentGameId, updatedGame, this.authService.getToken()).subscribe(
          () => {
            Swal.fire('Actualizado', 'Videojuego actualizado correctamente', 'success');
            const toastElement = document.getElementById('editGameToast');
            if (toastElement) {
              const toast = new bootstrap.Toast(toastElement);
              toast.hide();
            }
            this.loadVideogames();
          },
          error => {
            console.error('Error updating the game', error);
            Swal.fire('Error', 'No se pudo actualizar el videojuego', 'error');
          }
        );
      }
    }
  }

  mostrarFormularioAniadir(): void {
    this.addGameForm.reset();
    const toastElement = document.getElementById('addGameToast');
    if (toastElement) {
      const toast = new bootstrap.Toast(toastElement);
      toast.show();
    }
  }

  onSubmitAddGame(): void {
    if (this.addGameForm.valid) {
      const newGame = this.addGameForm.value;
      this.videogamesService.guardarVideojuego(newGame, this.authService.getToken()).subscribe(
        () => {
          Swal.fire('Añadido', 'Videojuego añadido correctamente', 'success');
          const toastElement = document.getElementById('addGameToast');
          if (toastElement) {
            const toast = new bootstrap.Toast(toastElement);
            toast.hide();
          }
          this.loadVideogames();
        },
        error => {
          console.error('Error adding the game', error);
          Swal.fire('Error', 'No se pudo añadir el videojuego', 'error');
        }
      );
    }
  }

  // ---- FAVORITES ----

  loadFavorites(): void {
    this.videogamesService.getFavorites().subscribe({
      next: (favoritos: VideojuegoDTO[]) => {
        this.favoritos.next(favoritos || []);
      },
      error: (err) => {
        console.error('Error loading favorites', err);
        // If not authenticated or server error, show a friendly message but keep the UI alive
        Swal.fire({
          icon: 'warning',
          title: 'Could not load favorites',
          text: err?.message || 'Please login or try again later'
        });
        this.favoritos.next([]);
      }
    });
  }

  isFavorite(game: VideojuegoDTO): boolean {
    return this.favoritos.getValue().some(fav => fav.id === game.id);
  }

  addWishList(event: Event, game: VideojuegoDTO): void {
    event.stopPropagation();
    const isCurrentlyFavorite = this.isFavorite(game);

    if (!isCurrentlyFavorite) {
      this.videogamesService.addFavorite(game.id).subscribe({
        next: () => {
          this.favoritos.next([...this.favoritos.getValue(), game]);
          Swal.fire('Añadido a favoritos', `${game.nombre} ha sido añadido a tus favoritos.`, 'success');
        },
        error: (err) => {
          console.error('Error adding favorite', err);
          Swal.fire('Error', 'No se pudo añadir a favoritos', 'error');
        }
      });
    } else {
      this.videogamesService.removeFavorite(game.id).subscribe({
        next: () => {
          this.favoritos.next(this.favoritos.getValue().filter(fav => fav.id !== game.id));
          Swal.fire('Eliminado de favoritos', `${game.nombre} ha sido eliminado de tus favoritos.`, 'success');
        },
        error: (err) => {
          console.error('Error removing favorite', err);
          Swal.fire('Error', 'No se pudo eliminar de favoritos', 'error');
        }
      });
    }
  }


  confirmarCompra(event: Event, game: VideojuegoDTO): void {
  event.stopPropagation(); 
  Swal.fire({
    title: '¿Estás seguro?',
    text: `¿Quieres comprar "${game.nombre}" por ${game.precio}€?`,
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: 'Sí, comprar',
    cancelButtonText: 'Cancelar',
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      const token = localStorage.getItem('token');
      if (token) {
        this.videogamesService.comprarJuego(game.id, token).subscribe({
          next: (res: any) => {
            Swal.fire('Éxito', res.message, 'success');
          },
          error: (err) => {
            console.error('Error al comprar el juego', err);
            Swal.fire('Error', err.error?.message || 'No se pudo completar la compra', 'error');
          }
        });
      } else {
        Swal.fire('No autenticado', 'Debes iniciar sesión para comprar.', 'warning');
      }
    }
  });
}



/*Método para desactivar videojuegos no disponibles para que no se compren */
  desactivarGame(gameId: number): void {
  const token = this.authService.getToken();
  Swal.fire({
    title: '¿Estás seguro?',
    text: 'El juego será marcado como no disponible, pero los usuarios que lo tengan comprado podrán seguir jugándolo.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, desactivar',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {
      this.videogamesService.desactivarVideojuego(gameId, token).subscribe(
        () => {
          Swal.fire('Desactivado', 'El videojuego fue marcado como no disponible.', 'success');
          this.loadVideogames();
        },
        error => {
          console.error('Error desactivando juego', error);
          Swal.fire('Error', 'No se pudo desactivar el videojuego', 'error');
        }
      );
    }
  });
}
}
