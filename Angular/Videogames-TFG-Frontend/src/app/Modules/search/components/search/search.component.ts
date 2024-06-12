import { Component } from '@angular/core';
import { VideogamesService } from '../../../../Services/videogames/videogames.service';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged, filter, map, tap } from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss'
})
export class SearchComponent {
  search = new FormControl('');

  constructor(private videojuegosService: VideogamesService) {
    this.search.valueChanges.pipe(
      map(value => value?.toLowerCase().trim() ?? ''),
      debounceTime(300),
      distinctUntilChanged(),
      filter(value => value !== '' && value.length > 2),
      /*tap(value => {
        console.log(value);
        this.videojuegosService.filterData(value);
      })*/
    ).subscribe();
  }
}
