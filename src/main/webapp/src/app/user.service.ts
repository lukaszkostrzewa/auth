import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {of, throwError} from 'rxjs';
import {User} from './user';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080/users';

  constructor(private http: HttpClient) {
  }

  isLoginTaken(login: string) {
    return this.http.head(`${this.url}/${login}`).pipe(
      map(() => true),
      catchError(this.handleError)
    );
  }

  registerUser(user: User) {
    return this.http.post(this.url, user);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 404) {
      return of(false);
    }
    return throwError('Something bad happened, please try again later.');
  }
}
