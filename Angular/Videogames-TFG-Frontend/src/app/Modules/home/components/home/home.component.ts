import { Component, OnInit } from '@angular/core';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { PaginatedResponse } from '../../../../Interfaces/DTO/PaginatedResponse';
import { AuthService } from '../../../../Services/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{



  games: VideojuegoDTO[] = [];
  currentPage: number = 0;
  pageSize: number = 6;
  totalElements: number = 0;

  constructor(private videogamesService: VideogamesService, private authService:AuthService) {}

  ngOnInit(): void {
    this.loadGames();
  }

  loadGames(): void {
    this.videogamesService.getVideojuegosPaginados(this.currentPage, this.pageSize).subscribe(
      (response: PaginatedResponse<VideojuegoDTO>) => {
        this.games = response.content;
        this.totalElements = response.totalElements;
      },
      (error) => {
        console.error('Error fetching games', error);
      }
    );
  }

  nextPage(): void {
    if ((this.currentPage + 1) * this.pageSize < this.totalElements) {
      this.currentPage++;
      this.loadGames();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadGames();
    }
  }

  navigateToGame(game: any): void {
    console.log('Navigating to game:', game.nombre);
  }


  isAdmin(): boolean {
    return this.authService.isAdminUser();
  }
  }


  /*games = [
    [
  {
    "id": 1,
    "nombre": "Elden Ring",
    "genero": "Action RPG",
    "descripcion": "A fantasy action RPG.",
    "anio_Publicacion": 2022,
    "precio": 59.99,
    "calificacion_por_edades": "Mature",
    "publicador": "FromSoftware",
    "plataformas": ["PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X/S", "PC"],
    "imagePath": "../../../../../assets/games/elden_ring.jpeg"
  },
  {
    "id": 2,
    "nombre": "Diablo IV",
    "genero": "Action RPG",
    "descripcion": "An action RPG.",
    "anio_Publicacion": 2023,
    "precio": 34.99,
    "calificacion_por_edades": "Mature",
    "publicador": "Blizzard Entertainment",
    "plataformas": ["PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X/S", "PC"],
    "imagePath": "../../../../../assets/games/diablo.jpg"
  },
  {
    "id": 3,
    "nombre": "DOTA 2",
    "genero": "MOBA",
    "descripcion": "A multiplayer online battle arena game.",
    "anio_Publicacion": 2013,
    "precio": 0,
    "calificacion_por_edades": "Teen",
    "publicador": "Valve Corporation",
    "plataformas": ["PC"],
    "imagePath": "../../../../../assets/games/dota2.jpg"
  },
  {
    "id": 4,
    "nombre": "The Witcher 3: Wild Hunt",
    "genero": "Action RPG",
    "descripcion": "An open-world action RPG.",
    "anio_Publicacion": 2015,
    "precio": 29.99,
    "calificacion_por_edades": "Mature",
    "publicador": "CD Projekt",
    "plataformas": ["PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X/S", "Nintendo Switch", "PC"],
    "imagePath": "../../../../../assets/games/The-Witcher-3-Wild-Hunt.jpg"
  },
  {
    "id": 5,
    "nombre": "Cyberpunk 2077",
    "genero": "Open-world RPG",
    "descripcion": "A futuristic open-world RPG.",
    "anio_Publicacion": 2020,
    "precio": 49.99,
    "calificacion_por_edades": "Mature",
    "publicador": "CD Projekt",
    "plataformas": ["PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X/S", "PC"],
    "imagePath": "../../../../../assets/games/cyberpunk.jpg"
  },
  {
    "id": 6,
    "nombre": "Hades",
    "genero": "Roguelike",
    "descripcion": "A roguelike dungeon crawler.",
    "anio_Publicacion": 2020,
    "precio": 24.99,
    "calificacion_por_edades": "Teen",
    "publicador": "Supergiant Games",
    "plataformas": ["Nintendo Switch", "PC"],
    "imagePath": "../../../../../assets/games/hades.png"
  },
  {
    "id": 7,
    "nombre": "Among Us",
    "genero": "Social Deduction",
    "descripcion": "A multiplayer social deduction game.",
    "anio_Publicacion": 2018,
    "precio": 4.99,
    "calificacion_por_edades": "Everyone 10+",
    "publicador": "Innersloth",
    "plataformas": ["Android", "iOS", "PC"],
    "imagePath": "../../../../../assets/games/among_us.jpeg"
  },
  {
    "id": 8,
    "nombre": "Assassin's Creed Valhalla",
    "genero": "Action-Adventure",
    "descripcion": "An open-world action-adventure game.",
    "anio_Publicacion": 2020,
    "precio": 59.99,
    "calificacion_por_edades": "Mature",
    "publicador": "Ubisoft",
    "plataformas": ["PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X/S", "PC"],
    "imagePath": "../../../../../assets/games/ac_valhalla.jpg"
  },
  {
    "id": 9,
    "nombre": "Minecraft",
    "genero": "Sandbox",
    "descripcion": "A sandbox game.",
    "anio_Publicacion": 2011,
    "precio": 26.95,
    "calificacion_por_edades": "Everyone 10+",
    "publicador": "Mojang Studios",
    "plataformas": ["PlayStation 4", "Xbox One", "Nintendo Switch", "PC", "Mobile"],
    "imagePath": "../../../../../assets/games/minecraft.jpg"
  },
  {
    "id": 10,
    "nombre": "Fortnite",
    "genero": "Battle Royale",
    "descripcion": "A battle royale game.",
    "anio_Publicacion": 2017,
    "precio": 0,
    "calificacion_por_edades": "Teen",
    "publicador": "Epic Games",
    "plataformas": ["PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series X/S", "Nintendo Switch", "PC", "Mobile"],
    "imagePath": "../../../../../assets/games/fortnite.jpg"
  },
  {
    "id": 11,
    "nombre": "League of Legends",
    "genero": "MOBA",
    "descripcion": "A multiplayer online battle arena game.",
    "anio_Publicacion": 2009,
    "precio": 0,
    "calificacion_por_edades": "Teen",
    "publicador": "Riot Games",
    "plataformas": ["PC"],
    "imagePath": "../../../../../assets/games/league_of_legends.jpg"
  },
  {
    "id": 12,
    "nombre": "Valorant",
    "genero": "First-person Shooter",
    "descripcion": "A tactical first-person shooter.",
    "anio_Publicacion": 2020,
    "precio": 0,
    "calificacion_por_edades": "Teen",
    "publicador": "Riot Games",
    "plataformas": ["PC"],
    "imagePath": "../../../../../assets/games/valorant.jpg"
  },
    {
        "id": 13,
        "nombre": "Final Fantasy VII Remake",
        "genero": "RPG",
        "descripcion": "A remake of the classic RPG.",
        "anio_Publicacion": 2020,
        "precio": 59.99,
        "calificacion_por_edades": "16+",
        "publicador": "Square Enix",
        "plataformas": ["PlayStation 4", "PlayStation 5", "Microsoft Windows"],
        "imagePath": "../../../../../assets/games/ffvii_remake.jpg"
    },
    {
        "id": 14,
        "nombre": "God of War",
        "genero": "Action-adventure",
        "descripcion": "An action-adventure game based on mythology.",
        "anio_Publicacion": 2018,
        "precio": 39.99,
        "calificacion_por_edades": "18+",
        "publicador": "Sony Interactive Entertainment",
        "plataformas": ["PlayStation 4", "Microsoft Windows"],
        "imagePath": "../../../../../assets/games/gow.jpg"
    },
    {
        "id": 15,
        "nombre": "Ratchet and Clank",
        "genero": "Action-platformer",
        "descripcion": "An action-platformer game.",
        "anio_Publicacion": 2016,
        "precio": 29.99,
        "calificacion_por_edades": "10+",
        "publicador": "Sony Interactive Entertainment",
        "plataformas": ["PlayStation 4"],
        "imagePath": "../../../../../assets/games/ratchet_and_clank.jpg"
    },
    {
        "id": 16,
        "nombre": "The Legend of Zelda: Breath of the Wild",
        "genero": "Action-adventure",
        "descripcion": "An open-world action-adventure game.",
        "anio_Publicacion": 2017,
        "precio": 59.99,
        "calificacion_por_edades": "12+",
        "publicador": "Nintendo",
        "plataformas": ["Nintendo Switch", "Wii U"],
        "imagePath": "../../../../../assets/games/BOTW.jpg"
    },
    {
        "id": 17,
        "nombre": "Super Mario Odyssey",
        "genero": "Platform",
        "descripcion": "A platform game.",
        "anio_Publicacion": 2017,
        "precio": 49.99,
        "calificacion_por_edades": "7+",
        "publicador": "Nintendo",
        "plataformas": ["Nintendo Switch"],
        "imagePath": "../../../../../assets/games/super_mario_odyssey.jpg"
    },
    {
        "id": 18,
        "nombre": "Red Dead Redemption 2",
        "genero": "Action-adventure",
        "descripcion": "An open-world action-adventure game set in the Wild West.",
        "anio_Publicacion": 2018,
        "precio": 59.99,
        "calificacion_por_edades": "18+",
        "publicador": "Rockstar Games",
        "plataformas": ["PlayStation 4", "Xbox One", "Microsoft Windows", "Stadia"],
        "imagePath": "../../../../../assets/games/rdr2.jpg"
    }

    {
        "id": 19,
        "nombre": "Splatoon 3",
        "genero": "Third-person shooter",
        "descripcion": "A colorful and fast-paced multiplayer shooter.",
        "anio_Publicacion": 2022,
        "precio": 59.99,
        "calificacion_por_edades": "10+",
        "publicador": "Nintendo",
        "plataformas": ["Nintendo Switch"],
        "imagePath": "../../../../../assets/games/splatoon_3.jpg"
    },
    {
        "id": 20,
        "nombre": "Animal Crossing: New Horizons",
        "genero": "Simulation",
        "descripcion": "A social simulation game where you build and manage your island.",
        "anio_Publicacion": 2020,
        "precio": 59.99,
        "calificacion_por_edades": "3+",
        "publicador": "Nintendo",
        "plataformas": ["Nintendo Switch"],
        "imagePath": "../../../../../assets/games/animal_crossing.jpg"
    },
    {
        "id": 21,
        "nombre": "Mario Kart 8 Deluxe",
        "genero": "Racing",
        "descripcion": "A kart racing game with various characters and tracks.",
        "anio_Publicacion": 2017,
        "precio": 59.99,
        "calificacion_por_edades": "3+",
        "publicador": "Nintendo",
        "plataformas": ["Nintendo Switch"],
        "imagePath": "../../../../../assets/games/mario_kart_8.jpg"
    },
    {
        "id": 22,
        "nombre": "Hollow Knight",
        "genero": "Metroidvania",
        "descripcion": "An action-adventure game with exploration and platforming elements.",
        "anio_Publicacion": 2017,
        "precio": 14.99,
        "calificacion_por_edades": "10+",
        "publicador": "Team Cherry",
        "plataformas": ["Microsoft Windows", "macOS", "Linux", "PlayStation 4", "Xbox One", "Nintendo Switch"],
        "imagePath": "../../../../../assets/games/hollow_knight.jpg"
    },
    {
        "id": 23,
        "nombre": "Stardew Valley",
        "genero": "Simulation",
        "descripcion": "A farming simulation game with RPG elements.",
        "anio_Publicacion": 2016,
        "precio": 14.99,
        "calificacion_por_edades": "7+",
        "publicador": "ConcernedApe",
        "plataformas": ["Microsoft Windows", "macOS", "Linux", "PlayStation 4", "Xbox One", "Nintendo Switch", "iOS", "Android"],
        "imagePath": "../../../../../assets/games/stardew_valley.jpg"
    },
    {
        "id": 24,
        "nombre": "Sekiro: Shadows Die Twice",
        "genero": "Action-adventure",
        "descripcion": "A challenging action-adventure game set in a fictional Japan.",
        "anio_Publicacion": 2019,
        "precio": 59.99,
        "calificacion_por_edades": "18+",
        "publicador": "Activision",
        "plataformas": ["PlayStation 4", "Xbox One", "Microsoft Windows", "Stadia"],
        "imagePath": "../../../../../assets/games/sekiro.jpg"
    },
    {
        "id": 25,
        "nombre": "Celeste",
        "genero": "Platformer",
        "descripcion": "A challenging platformer game with a touching story.",
        "anio_Publicacion": 2018,
        "precio": 19.99,
        "calificacion_por_edades": "10+",
        "publicador": "Matt Makes Games",
        "plataformas": ["Microsoft Windows", "macOS", "Linux", "PlayStation 4", "Xbox One", "Nintendo Switch"],
        "imagePath": "../../../../../assets/games/celeste.jpg"
    },
    {
        "id": 26,
        "nombre": "Horizon Zero Dawn",
        "genero": "Action RPG",
        "descripcion": "An open-world action RPG set in a post-apocalyptic world.",
        "anio_Publicacion": 2017,
        "precio": 49.99,
        "calificacion_por_edades": "16+",
        "publicador": "Sony Interactive Entertainment",
        "plataformas": ["PlayStation 4", "Microsoft Windows"],
        "imagePath": "../../../../../assets/games/horizon_zero_dawn.jpg"
    },
    {
        "id": 27,
        "nombre": "Persona 5",
        "genero": "RPG",
        "descripcion": "A role-playing game where you live a double life as a high school student and a phantom thief.",
        "anio_Publicacion": 2016,
        "precio": 59.99,
        "calificacion_por_edades": "16+",
        "publicador": "Atlus",
        "plataformas": ["PlayStation 4", "PlayStation 3"],
        "imagePath": "../../../../../assets/games/persona_5.jpg"
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


