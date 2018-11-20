import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {User} from './user';
import {of, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private url = 'http://localhost:8080/sessions';

  constructor(private http: HttpClient) {
  }

  login(user: User) {
    return this.http.post(this.url, user).pipe(
      map(() => true),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 401) {
      return of(false);
    }
    return throwError('Something bad happened, please try again later.');
  }
}
