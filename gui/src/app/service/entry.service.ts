import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CustomResponse } from '../interface/custom-response';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Entry } from '../interface/entry';

@Injectable({
  providedIn: 'root'
})
export class EntryService {
  private apiUrl = 'http://localhost:8080/entry';

  constructor(private http: HttpClient) {}

  entries$ = <Observable<CustomResponse>>(
    this.http.get<CustomResponse>(`${this.apiUrl}/list`).pipe(tap(console.log), catchError(this.handleError))
  );

  save$ = (entry: Entry) =>
    <Observable<CustomResponse>>(
      this.http.post<CustomResponse>(`${this.apiUrl}/save`, entry).pipe(tap(console.log), catchError(this.handleError))
    );

  update$ = (entry: Entry) =>
    <Observable<CustomResponse>>(
      this.http.put<CustomResponse>(`${this.apiUrl}/update`, entry).pipe(tap(console.log), catchError(this.handleError))
    );

  delete$ = (entryId: number) =>
    <Observable<CustomResponse>>(
      this.http.delete<CustomResponse>(`${this.apiUrl}/delete/${entryId}`).pipe(tap(console.log), catchError(this.handleError))
    );

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(`An error occured - Error code: ${error.status}}`);
  }
}
