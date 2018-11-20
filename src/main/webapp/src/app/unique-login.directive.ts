import {Directive} from '@angular/core';
import {UserService} from './user.service';
import {AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS, ValidationErrors} from '@angular/forms';
import {Observable} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Directive({
  selector: '[appUniqueLogin]',
  providers: [{provide: NG_ASYNC_VALIDATORS, useExisting: UniqueLoginDirective, multi: true}]
})
export class UniqueLoginDirective implements AsyncValidator {

  constructor(private userService: UserService) {
  }

  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    return this.userService.isLoginTaken(control.value).pipe(
      map(isTaken => (isTaken ? {notUniqueLogin: true} : null)),
      catchError(() => null)
    );
  }
}
