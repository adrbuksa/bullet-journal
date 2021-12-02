import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';
import { DataState } from './enum/data-state.enum';
import { EntryStatus } from './enum/entry-status';
import { EntryType } from './enum/entry-type';
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
  readonly EntryType = EntryType;
  private selectedDateSubject = new BehaviorSubject<Date>(new Date());
  selectedDate$ = this.selectedDateSubject.asObservable();
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();

  constructor(private entryService: EntryService) {}

  ngOnInit(): void {
    this.appState$ = this.entryService.entries$.pipe(
      map(response => {
        this.dataSubject.next(response);
        this.filterEntriesByDate(this.selectedDateSubject.value);
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
      startWith({ dataState: DataState.LOADING_STATE, appData: this.dataSubject.value }),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error });
      })
    );
  }

  saveEntry(entryForm: NgForm) {
    this.isLoadingSubject.next(true);
    let entry = entryForm.value as Entry;
    entry.date = this.selectedDateSubject.value;
    entry.status = EntryStatus.NOT_COMPLETED;
    this.appState$ = this.entryService.save$(entry).pipe(
      map(response => {
        this.dataSubject.next({
          ...response,
          data: { entries: [...this.dataSubject.value.data.entries, response.data.entry] }
        });

        document.getElementById('closeModal').click();
        entryForm.resetForm({ type: this.EntryType.TASK });
        this.filterEntriesByDate(this.selectedDateSubject.value);

        this.isLoadingSubject.next(false);
        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value
        };
      }),
      startWith({
        dataState: DataState.LOADING_STATE,
        appData: this.dataSubject.value
      }),
      catchError((error: string) => {
        this.isLoadingSubject.next(false);
        return of({ dataState: DataState.ERROR_STATE, error });
      })
    );
  }

  deleteEntry(entry: Entry) {
    this.isLoadingSubject.next(true);
    this.appState$ = this.entryService.delete$(entry.id).pipe(
      map(response => {
        this.dataSubject.next({
          ...response,
          data: { entries: [...this.dataSubject.value.data.entries.filter(e => e.id !== entry.id)] }
        });

        this.filterEntriesByDate(this.selectedDateSubject.value);
        this.isLoadingSubject.next(false);
        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value
        };
      }),
      startWith({
        dataState: DataState.LOADING_STATE,
        appData: this.dataSubject.value
      }),
      catchError((error: string) => {
        this.isLoadingSubject.next(false);
        return of({ dataState: DataState.ERROR_STATE, error });
      })
    );
  }

  onNextDayClick(): void {
    let date = new Date(this.selectedDateSubject.value);
    date.setDate(date.getDate() + 1);
    this.selectedDateSubject.next(date);
    this.filterEntriesByDate(this.selectedDateSubject.value);
  }

  onPreviousDayClick(): void {
    let date = new Date(this.selectedDateSubject.value);
    date.setDate(date.getDate() - 1);
    this.selectedDateSubject.next(date);
    this.filterEntriesByDate(this.selectedDateSubject.value);
  }
}
