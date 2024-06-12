import { Component } from '@angular/core';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { AuthService } from '../../../../Services/auth/auth.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.scss'
})
export class WishlistComponent {
  favoritos: VideojuegoDTO[] = [];

  constructor(private videogamesService: VideogamesService, private authService: AuthService) { }

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

  }
}
