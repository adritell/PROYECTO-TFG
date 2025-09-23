import { Component, OnInit } from '@angular/core';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { PaginatedResponse } from '../../../../Interfaces/DTO/PaginatedResponse';

import Swal from 'sweetalert2';
@Component({
  selector: 'app-my-games',
  templateUrl: './myGames.component.html',
  styleUrls: ['./myGames.component.scss']
})
export class MyGamesComponent implements OnInit {

  myGames: VideojuegoDTO[] = [];
  loading = false;
  error: string | null = null;

  // Paginación
  currentPage: number = 0;
  pageSize: number = 6;
  totalPages: number = 0;

  constructor(private videogamesService: VideogamesService) {}

  ngOnInit(): void {
    this.loadMyGames();
  }

  loadMyGames(page: number = 0): void {
    this.loading = true;
    this.error = null;

    this.videogamesService.getMyGamesPaginated(page, this.pageSize).subscribe({
      next: (response: PaginatedResponse<VideojuegoDTO>) => {
        this.myGames = response.content || [];
        this.totalPages = response.totalPages ?? 0;
        this.currentPage = response.number ?? page;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando mis juegos', err);
        this.error = err?.error?.message || 'Error al cargar tus juegos.';
        this.loading = false;
      }
    });
  }

  changePage(page: number): void {
    if (page < 0 || page >= this.totalPages) return;
    this.loadMyGames(page);
  }

  confirmarCompra(game: VideojuegoDTO): void {
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
        this.comprarJuego(game.id);
      }
    });
  }

  private comprarJuego(gameId: number): void {
    const token = localStorage.getItem('token') || '';
    if (!token) {
      Swal.fire('No autenticado', 'Debes iniciar sesión para comprar.', 'warning');
      return;
    }

    this.videogamesService.comprarJuego(gameId, token).subscribe({
      next: (res) => {
        Swal.fire(
          'Compra realizada',
          res?.juego ? `Has comprado: ${res.juego}` : 'Compra realizada con éxito',
          'success'
        );
        // recargar la página actual de comprados
        this.loadMyGames(this.currentPage);
      },
      error: (err) => {
        console.error('Error en compra', err);
        const message = err?.error?.message || 'No se pudo completar la compra';
        Swal.fire('Error', message, 'error');
      }
    });
  }
}