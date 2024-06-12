import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home/home.component';
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SearchModule } from '../search/search.module';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { DetallesJuegoComponent } from './components/detalles-juego/detalles-juego.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'wishlist', component: WishlistComponent},
  { path: 'detalles-juego/:id', component: DetallesJuegoComponent },
]

@NgModule({
  declarations: [
    HomeComponent,
    WishlistComponent,
    DetallesJuegoComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    ReactiveFormsModule,
    SearchModule,
    FormsModule
  ],
  exports: [
    RouterModule
  ]
})
export class HomeModule { }
