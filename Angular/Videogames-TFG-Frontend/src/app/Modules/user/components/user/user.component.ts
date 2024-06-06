import { tap } from 'rxjs';
import { Component } from '@angular/core';
import { UsuarioAdminResponse } from '../../../../Interfaces/DTO/UsuarioAdminResponse';
import { UsuarioResponse } from '../../../../Interfaces/DTO/UsuarioResponse';
import { UsersService } from '../../../../Services/users/users.service';
import { AuthService } from '../../../../Services/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup } from '@angular/forms';


declare var bootstrap: any;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent {

  editUserForm: FormGroup;
  adminUsers: UsuarioAdminResponse[] = [];
  normalUsers: UsuarioResponse[] = [];
  currentUserId: number;

  constructor(private userService: UsersService, private authService: AuthService, private fb:FormBuilder) {


  }

  ngOnInit(): void {
    this.editUserForm = this.fb.group({
      nombre: [''],
      apellidos: [''],
      email: [''],
      activo: [true],
      roles: ['ROLE_USER']
    });

    this.userService.getAllUsuarios().subscribe(
      (data) => {
        if (this.authService.isAdminUser()) {
          this.adminUsers = data as UsuarioAdminResponse[];
        } else {
          this.normalUsers = data as UsuarioResponse[];
        }
      },
      (error) => {
        console.error('Error fetching users', error);
      }
    );

  }

  isAdmin(): boolean {
    return this.authService.isAdminUser();
  }

  eliminarUsuario(id: number): void {
    this.userService.eliminarUsuario(id).subscribe(
      () => {
        // Elimina el usuario de la lista localmente
        this.adminUsers = this.adminUsers.filter(user => user.id !== id);
        Swal.fire('Eliminado', 'Usuario eliminado correctamente', 'success');
      },
      (error: HttpErrorResponse) => {
        console.error('Error eliminando el usuario', error);
        Swal.fire('Error', 'No se pudo eliminar el usuario', 'error');
      }
    );
  }

  editarUsuario(id: number): void {
    this.currentUserId = id;
    const user = this.adminUsers.find(user => user.id === id);
    if (user) {
      this.editUserForm.patchValue({
        nombre: user.nombre,
        apellidos: user.apellidos,
        email: user.email,
        activo: user.activo
      });
      const toastElement = document.getElementById('editUserToast');
      if (toastElement) {
        const toast = new bootstrap.Toast(toastElement);
        toast.show();
      }
    }
  }

  onSubmit(): void {
    if (this.editUserForm.valid) {
      const updatedUser = this.editUserForm.value;
      // Asegurarse de que roles sea un array para que el backend pueda deserializarlo correctamente
      updatedUser.roles = [updatedUser.roles];
      this.userService.editarUsuario(this.currentUserId, updatedUser).subscribe(
        (response) => {
          const index = this.adminUsers.findIndex(user => user.id === this.currentUserId);
          if (index !== -1) {
            this.adminUsers[index] = response;
          }
          Swal.fire('Actualizado', 'Usuario actualizado correctamente', 'success');
          const toastElement = document.getElementById('editUserToast');
          if (toastElement) {
            const toast = new bootstrap.Toast(toastElement);
            toast.hide();
          }
        },
        (error) => {
          console.error('Error editando el usuario', error);
          Swal.fire('Error', 'No se pudo actualizar el usuario', 'error');
        }
      );
    }
  }
}
