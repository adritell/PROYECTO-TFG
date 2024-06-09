import { Component, OnInit } from '@angular/core';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { PaginatedResponse } from '../../../../Interfaces/DTO/PaginatedResponse';
import { AuthService } from '../../../../Services/auth/auth.service';
import { Observable, catchError, map, tap, throwError } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';

declare var bootstrap: any;


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{


  currentPage: number = 0;
  pageSize: number = 6;
  totalElements: number = 0;
  totalPages: number = 0;
  editGameForm: FormGroup;
  addGameForm: FormGroup;
  currentGameId: number | null = null;
  videogames$: Observable<VideojuegoDTO[]>;


  constructor(private fb: FormBuilder, private videogamesService: VideogamesService, private authService: AuthService) {
    this.videogames$ = this.videogamesService.videogames$;
  }

  ngOnInit(): void {
    this.loadVideogames();


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
    this.videogames$ = this.videogamesService.getVideojuegosPaginados(this.currentPage, this.pageSize).pipe(
      tap(response => {
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
      }),
      map(response => {
        if (Array.isArray(response.content)) {
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
          text: error.message
        });
        return throwError(error);
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

  // Calcula las páginas a mostrar en el paginado
  getDisplayedPages(): number[] {
    // Número total de páginas a mostrar en la paginación
    const totalPagesToShow = 5;
    const pages: number[] = [];

    if (this.totalPages <= totalPagesToShow) {
      // Mostrar todas las páginas si el total de páginas es menor o igual al número de páginas a mostrar
      for (let i = 0; i < this.totalPages; i++) {
        pages.push(i);
      }
    } else {
      // Siempre mostrar la primera página
      pages.push(0);

      // Mostrar puntos suspensivos si la página actual es mayor que 2 se añade -1 para indicar que hay
      //páginas antes de la actual que no se están mostrando explícitamente
      if (this.currentPage > 2) {
        pages.push(-1);
      }

      // Calcular el índice de la página de inicio y de fin a mostrar
      const startPage = Math.max(1, this.currentPage - 1);
      const endPage = Math.min(this.totalPages - 2, this.currentPage + 1);

      // Agregar las páginas calculadas al array
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }

      // Mostrar puntos suspensivos si la página actual es menor que totalPages - 3 se añade -1 para indicar
      //que hay páginas después de las mostradas que no se están mostrando explícitamente.
      if (this.currentPage < this.totalPages - 3) {
        pages.push(-1);
      }

      // Siempre mostrar la última página
      pages.push(this.totalPages - 1);
    }

    return pages;
  }





  goToPage(page: number): void {
    this.currentPage = page;
    this.loadVideogames();
  }


  navigateToGame(game: VideojuegoDTO): void {
    console.log('Navigating to game:', game.id);
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
          response => {
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




  //Método para mostrar el formulario de añadir un videojuego
  mostrarFormularioAniadir(): void {
    this.addGameForm.reset();
    const toastElement = document.getElementById('addGameToast');
    if (toastElement) {
      const toast = new bootstrap.Toast(toastElement);
      toast.show();
    }
  }


  //Método para añadir un videojuego
  onSubmitAddGame(): void {
    if (this.addGameForm.valid) {
      const newGame = this.addGameForm.value;
      this.videogamesService.guardarVideojuego(newGame, this.authService.getToken()).subscribe(
        response => {
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




  addWishList(event: Event, game: VideojuegoDTO): void {
    event.stopPropagation();
    const target = event.target as HTMLElement;
    const icon = target.closest('.btn')?.querySelector('i');
    if (icon) {
      icon.classList.toggle('text-danger');
      // Aquí es donde se implementará la lógica para añadir o quitar el juego de la lista de deseos del usuario
      if (icon.classList.contains('text-danger')) {
        console.log(`Añadir a la lista de deseos: ${game.nombre}`);
      } else {
        console.log(`Quitar de la lista de deseos: ${game.nombre}`);
      }
    }
  }
}


  /*games = [
    [
    {
    "id": 28,
    "nombre": "Sonic Mania",
    "genero": "Platformer",
    "descripcion": "A 2D platformer featuring Sonic the Hedgehog.",
    "anio_Publicacion": 2017,
    "precio": 19.99,
    "calificacion_por_edades": "Everyone",
    "publicador": "Sega",
    "plataformas": ["PlayStation 4", "Xbox One", "Nintendo Switch", "Microsoft Windows"],
    "imagePath": "../../../../../assets/games/sonic_mania.jpg"
  },
  {
    "id": 29,
    "nombre": "Spyro Reignited Trilogy",
    "genero": "Platformer",
    "descripcion": "A collection of remastered Spyro games.",
    "anio_Publicacion": 2018,
    "precio": 39.99,
    "calificacion_por_edades": "Everyone 10+",
    "publicador": "Activision",
    "plataformas": ["PlayStation 4", "Xbox One", "Nintendo Switch", "Microsoft Windows"],
    "imagePath": "../../../../../assets/games/spyro_reignited_trilogy.jpg"
  },
  {
    "id": 29,
    "nombre": "Kirby's and the Forgotten Island",
    "genero": "Platformer",
    "descripcion": "A platformer game with a unique yarn-based art style.",
    "anio_Publicacion": 2022,
    "precio": 39.99,
    "calificacion_por_edades": "Everyone",
    "publicador": "Nintendo",
    "plataformas": ["Nintendo Switch"],
    "imagePath": "../../../../../assets/games/kirby_y_la_tierra_olvidada.jpg"
  },

  {
    "id": 30,
    "nombre": "Sonic Forces",
    "genero": "Platformer",
    "descripcion": "A platformer game where Sonic and his friends fight against Dr. Eggman.",
    "anio_Publicacion": 2017,
    "precio": 39.99,
    "calificacion_por_edades": "Everyone 10+",
    "publicador": "Sega",
    "plataformas": ["PlayStation 4", "Xbox One", "Nintendo Switch", "Microsoft Windows"],
    "imagePath": "../../../../../assets/games/sonic_forces.jpg"
  }

  {
    "id": 30,
    "nombre": "Sonic Frontiers",
    "genero": "Open-world Adventure",
    "descripcion": "An open-world adventure game starring Sonic the Hedgehog.",
    "anio_Publicacion": 2022,
    "precio": 59.99,
    "calificacion_por_edades": "Everyone 10+",
    "publicador": "Sega",
    "plataformas": ["PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X/S", "Nintendo Switch", "Microsoft Windows"],
    "imagePath": "../../../../../assets/games/sonic_frontiers.jpg"
  },

  {
    "id": 31,
    "nombre": "Super Smash Bros. Ultimate",
    "genero": "Fighting",
    "descripcion": "A crossover fighting game featuring various Nintendo characters.",
    "anio_Publicacion": 2018,
    "precio": 59.99,
    "calificacion_por_edades": "Everyone 10+",
    "publicador": "Nintendo",
    "plataformas": ["Nintendo Switch"],
    "imagePath": "../../../../../assets/games/super_smash_bros_ultimate.jpg"
  },

  {
    "id": 32,
    "nombre": "FIFA 24",
    "genero": "Sports",
    "descripcion": "The latest installment in the FIFA series featuring realistic football gameplay.",
    "anio_Publicacion": 2023,
    "precio": 59.99,
    "calificacion_por_edades": "Everyone",
    "publicador": "Electronic Arts",
    "plataformas": ["PlayStation 5", "Xbox Series X/S", "PC"],
    "imagePath": "../../../../../assets/games/fifa24.jpg"
  },
  {
    "id": 33,
    "nombre": "Call of Duty: Modern Warfare 3",
    "genero": "First-person Shooter",
    "descripcion": "An intense first-person shooter set in a modern warfare setting.",
    "anio_Publicacion": 2011,
    "precio": 29.99,
    "calificacion_por_edades": "Mature",
    "publicador": "Activision",
    "plataformas": ["PlayStation 3", "Xbox 360", "PC"],
    "imagePath": "../../../../../assets/games/call_of_dutty_mw3.jpg"
  }

  {
    "id": 34,
    "nombre": "Titanfall 2",
    "genero": "First-person Shooter",
    "descripcion": "A fast-paced first-person shooter with parkour elements.",
    "anio_Publicacion": 2016,
    "precio": 19.99,
    "calificacion_por_edades": "Mature",
    "publicador": "Electronic Arts",
    "plataformas": ["PlayStation 4", "Xbox One", "Microsoft Windows"],
    "imagePath": "../../../../../assets/games/titanfall2.jpg"
  },


  {
    "id": 35,
    "nombre": "Destiny 2",
    "genero": "First-person Shooter",
    "descripcion": "An online multiplayer first-person shooter with RPG elements.",
    "anio_Publicacion": 2017,
    "precio": 0,
    "calificacion_por_edades": "Teen",
    "publicador": "Bungie",
    "plataformas": ["PlayStation 4", "Xbox One", "Microsoft Windows"],
    "imagePath": "../../../../../assets/games/destiny2.jpg"
  },

  {
  "id": 36,
  "nombre": "Ratchet and Clank: Rift Apart",
  "genero": "Action-platformer",
  "descripcion": "An action-platformer featuring dimensional travel and breathtaking graphics.",
  "anio_Publicacion": 2021,
  "precio": 69.99,
  "calificacion_por_edades": "Everyone 10+",
  "publicador": "Sony Interactive Entertainment",
  "plataformas": ["PlayStation 5"],
  "imagePath": "../../../../../assets/games/ratchet_and_clank_rift_apart.jpg"
  }

  {
  "id": 37,
  "nombre": "Spider-Man 2",
  "genero": "Action-Adventure",
  "descripcion": "An action-packed adventure featuring the iconic web-slinging superhero.",
  "anio_Publicacion": 2023,
  "precio": 59.99,
  "calificacion_por_edades": "Teen",
  "publicador": "Insomniac Games",
  "plataformas": ["PlayStation 5"],
  "imagePath": "../../../../../assets/games/spiderman_2.jpg"
}






  ];

  constructor() {}

  ngOnInit(): void {}



  navigateToGame(game: any): void {
    // Comentado por ahora
    // this.router.navigate(['/game', game.nombre]);
    console.log('Navigating to game:', game.nombre);
  }
  }
  */


