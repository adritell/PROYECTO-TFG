import { Component, OnInit } from '@angular/core';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { AuthService } from '../../../../Services/auth/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.scss'
})
export class WishlistComponent implements OnInit {
  favoritos: VideojuegoDTO[] = [];

  constructor(private router: Router, private videogamesService: VideogamesService, private authService: AuthService) { }

  ngOnInit(): void {
    console.log('Loading favorites...');
    this.loadFavorites();
  }

  loadFavorites(): void {
    this.videogamesService.getFavorites().subscribe({
      next: (favoritos: VideojuegoDTO[]) => {
        console.log('Favorites:', favoritos);
        this.favoritos = favoritos || [];
      },
      error: (err) => {
        console.error('Error loading favorites', err);
        Swal.fire({
          icon: 'warning',
          title: 'Could not load favorites',
          text: err?.message || 'Please login or try again later'
        });
        this.favoritos = [];
      }
    });
  }

  // Navegar a los detalles del juego
  navigateToGame(game: VideojuegoDTO): void {
    this.router.navigate(['/detalles-juego', game.id]);
  }
}
