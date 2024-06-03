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
    // Añade más juegos aquí...
  ];

  constructor() {}

  ngOnInit(): void {}

}
