import { Component } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { DataState } from './enum/data-state.enum';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { Entry } from './interface/entry';
import { EntryService } from './service/entry.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  appState$: Observable<AppState<CustomResponse>>;
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  readonly DataState = DataState;
  private selectedDateSubject = new BehaviorSubject<Date>(new Date());
  selectedDate$ = this.selectedDateSubject.asObservable();

  constructor(private entryService: EntryService) {}

  ngOnInit(): void {
    this.appState$ = this.entryService.entries$.pipe(
      map(response => {
        this.dataSubject.next(response);
        return { dataState: DataState.LOADED_STATE, appData: { ...response, data: { entries: response.data.entries } } };
      }),
      startWith({ dataState: DataState.LOADING_STATE }),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error });
      })
    );
  }

  filterEntriesByDate(date: Date): void {
    this.appState$ = this.entryService.filterByDate$(date, this.dataSubject.value).pipe(
      map(response => {
        return { dataState: DataState.LOADED_STATE, appData: response };
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value }),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error });
      })
    );
  }
}
