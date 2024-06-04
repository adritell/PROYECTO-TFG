import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  games = [
    {
      title: 'Elden Ring',
      description: 'A fantasy action RPG.',
      imagePath: '../../../../../assets/games/elden_ring.jpeg',
      price: '59,99€'
    },
    {
      title: 'Diablo IV',
      description: 'An action RPG.',
      imagePath: '../../../../../assets/games/diablo.jpg',
      price: '34,99€'
    },
    {
      title: 'DOTA 2',
      description: 'A multiplayer online battle arena game.',
      imagePath: '../../../../../assets/games/dota2.jpg',
      price: 'Free to Play'
    },
    {
      title: 'The Witcher 3: Wild Hunt',
      description: 'An open-world action RPG.',
      imagePath: '../../../../../assets/games/witcher3.jpg',
      price: '29,99€'
  },
  {
      title: 'Cyberpunk 2077',
      description: 'A futuristic open-world RPG.',
      imagePath: '../../../../../assets/games/cyberpunk2077.jpg',
      price: '49,99€'
  },
  {
      title: 'Hades',
      description: 'A roguelike dungeon crawler.',
      imagePath: '../../../../../assets/games/hades.jpg',
      price: '24,99€'
  },
  {
      title: 'Among Us',
      description: 'A multiplayer social deduction game.',
      imagePath: '../../../../../assets/games/among_us.jpg',
      price: '4,99€'
  },
  {
      title: 'Assassin\'s Creed Valhalla',
      description: 'An open-world action-adventure game.',
      imagePath: '../../../../../assets/games/ac_valhalla.jpg',
      price: '59,99€'
  },
  {
      title: 'Minecraft',
      description: 'A sandbox game.',
      imagePath: '../../../../../assets/games/minecraft.jpg',
      price: '26,95€'
  },
  {
      title: 'Fortnite',
      description: 'A battle royale game.',
      imagePath: '../../../../../assets/games/fortnite.jpg',
      price: 'Free to Play'
  },
  {
      title: 'League of Legends',
      description: 'A multiplayer online battle arena game.',
      imagePath: '../../../../../assets/games/league_of_legends.jpg',
      price: 'Free to Play'
  },
  {
      title: 'Valorant',
      description: 'A tactical first-person shooter.',
      imagePath: '../../../../../assets/games/valorant.jpg',
      price: 'Free to Play'
  }
  ];

  constructor() {}

  ngOnInit(): void {}



  navigateToGame(game: any): void {
    // Comentado por ahora
    // this.router.navigate(['/game', game.title]);
    console.log('Navigating to game:', game.title);
  }

}
