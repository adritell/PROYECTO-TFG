import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
} from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Obtener el token de localStorage
    const token = localStorage.getItem('token');

    // Clonar la solicitud y añadir el token en el header si existe
    let authReq = req;
    if (token) {
      authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`),
      });
    }

    // Manejar la solicitud y la respuesta
    return next.handle(authReq).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          // Obtener el token del header de la respuesta
          const newToken = event.headers.get('Authorization');
          if (newToken) {
            console.log("Nuevo Token: ",newToken);
            // Guardar el nuevo token en localStorage
            localStorage.setItem('token', newToken.replace('Bearer ', ''));
          }
        }
      })
    );
  }
}
