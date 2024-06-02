import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard } from './Guards/login.guard';

const routes: Routes = [
  {path: "auth", loadChildren: () => import('./Modules/auth/auth.module').then(m => m.AuthModule)},
  {path: "", loadChildren: () => import('./Modules/home/home.module').then(m => m.HomeModule), canActivate:[LoginGuard]},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
