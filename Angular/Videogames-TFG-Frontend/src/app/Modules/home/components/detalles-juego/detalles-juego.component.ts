import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { ComentarioResponse } from '../../../../Interfaces/DTO/ComentarioResponse';
import { ComentarioRequest } from '../../../../Interfaces/DTO/ComentarioRequest';
import { CommentsService } from '../../../../Services/comments/comments.service';
import { AuthService } from '../../../../Services/auth/auth.service';

@Component({
  selector: 'app-detalles-juego',
  templateUrl: './detalles-juego.component.html',
  styleUrl: './detalles-juego.component.scss'
})
export class DetallesJuegoComponent {
  juego: VideojuegoDTO | undefined;
  comentarios: ComentarioResponse[] = [];
  comentariosCargados: boolean = false;
  nuevoComentario: string = '';
  // Comentario en edición
  editandoComentario: ComentarioResponse | null = null;
  // Email del usuario actual
  currentUserEmail: string = '';

  constructor(
    private route: ActivatedRoute,
    private videogamesService: VideogamesService,
    private commentService: CommentsService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.videogamesService.getGameById(Number(id)).subscribe(juego => {
        this.juego = juego;
      });

      this.videogamesService.getComentariosByVideojuego(Number(id)).subscribe(comentarios => {
        this.comentarios = comentarios;
        this.comentariosCargados = true;
      });
    }

    const email = this.authService.getCurrentUserEmail();
    console.log(email);
    if (email) {
      this.currentUserEmail = email;
    }
  }

  comprarJuego(): void {
    console.log('Comprar juego');
  }

  agregarComentario(): void {
    const comentarioRequest: ComentarioRequest = {
      text: this.nuevoComentario,
      game: this.juego?.nombre || ''
    };
    const token = localStorage.getItem('token'); // Asume que el token está almacenado en localStorage
    if (token) {
      this.commentService.addComentario(comentarioRequest, token).subscribe(() => {
        // Actualiza la lista de comentarios después de añadir uno nuevo
        this.ngOnInit();
        this.nuevoComentario = '';
      });
    }
  }

  editarComentario(comentario: ComentarioResponse): void {
    this.editandoComentario = comentario;
  }

  actualizarComentario(): void {
    if (this.editandoComentario) {
      const comentarioRequest: ComentarioRequest = {
        text: this.editandoComentario.text,
        game: this.juego?.nombre || ''
      };
      this.commentService.updateComentario(this.editandoComentario.id, comentarioRequest).subscribe(() => {
        // Actualiza la lista de comentarios después de editar uno
        this.ngOnInit();
        this.editandoComentario = null;
      });
    }
  }

  eliminarComentario(id: number): void {
    this.commentService.deleteComentario(id).subscribe(() => {
      // Actualiza la lista de comentarios después de eliminar uno
      this.ngOnInit();
    });
  }

  isAdmin(): boolean {
    return this.authService.isAdminUser();
  }
}
