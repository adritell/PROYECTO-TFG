import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './components/user/user.component';
import { UsersService } from '../../Services/users/users.service';
import { ProfileComponent } from './components/profile/profile.component';



const routes: Routes = [
  {path: 'users', component: UserComponent},
  {path: 'profile', component: ProfileComponent},

];

@NgModule({
  declarations: [
    UserComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ],
  providers: [
    UsersService
  ],
  exports: [
    RouterModule
  ]
})
export class UserModule { }
