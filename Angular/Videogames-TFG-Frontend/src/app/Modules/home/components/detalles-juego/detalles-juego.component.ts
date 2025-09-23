import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { VideojuegoDTO } from '../../../../Interfaces/DTO/VideojuegoDTO';
import { ComentarioResponse } from '../../../../Interfaces/DTO/ComentarioResponse';
import { ComentarioRequest } from '../../../../Interfaces/DTO/ComentarioRequest';
import { CommentsService } from '../../../../Services/comments/comments.service';
import { AuthService } from '../../../../Services/auth/auth.service';
import Swal from 'sweetalert2';

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
  if (!this.juego) return;

  Swal.fire({
    title: '¿Estás seguro?',
    text: `¿Quieres comprar "${this.juego.nombre}" por ${this.juego.precio}€?`,
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: 'Sí, comprar',
    cancelButtonText: 'Cancelar',
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      const token = localStorage.getItem('token');
      if (token) {
        this.videogamesService.comprarJuego(this.juego!.id, token).subscribe({
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
