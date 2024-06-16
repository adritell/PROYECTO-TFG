import { Component } from '@angular/core';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { AuthService } from '../../../../Services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.scss'
})
export class WishlistComponent {
  favoritos: VideojuegoDTO[] = [];

  constructor(private router:Router ,private videogamesService: VideogamesService, private authService: AuthService) { }

  ngOnInit(): void {
    console.log('Loading favorites...');  // Depuración
    this.loadFavorites();
  }

  loadFavorites(): void {
    const userId = this.authService.getCurrentUserId();
    console.log('Current User ID:', userId);  // Depuración
    if (userId !== null) {
      this.videogamesService.getFavorites(userId).subscribe(favoritos => {
        console.log('Favorites:', favoritos);  // Depuración
        this.favoritos = favoritos;
      });
    }
  }

  // Navegar a los detalles del juego
  navigateToGame(game: VideojuegoDTO): void {
    this.router.navigate(['/detalles-juego', game.id]);
  }
}
