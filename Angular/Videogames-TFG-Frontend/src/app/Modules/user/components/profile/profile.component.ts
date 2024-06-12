import { Component } from '@angular/core';
import { UsuarioResponse } from '../../../../Interfaces/DTO/UsuarioResponse';
import { UsuarioAdminResponse } from '../../../../Interfaces/DTO/UsuarioAdminResponse';
import { UsersService } from '../../../../Services/users/users.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../Services/auth/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

  user: UsuarioAdminResponse | UsuarioResponse | null = null;
  isAdmin: boolean = false;

  constructor(
    private userService: UsersService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId();
    if (userId) {
      this.isAdmin = this.authService.isAdminUser();
      this.userService.getUsuarioById(+userId).subscribe(
        (data) => {
          this.user = data;
        },
        (error) => {
          console.error('Error fetching user data', error);
        }
      );
    } else {
      console.error('User ID is null');
    }
  }

  isUsuarioAdminResponse(user: UsuarioAdminResponse | UsuarioResponse): user is UsuarioAdminResponse {
    return (user as UsuarioAdminResponse).roles !== undefined;
  }
}
