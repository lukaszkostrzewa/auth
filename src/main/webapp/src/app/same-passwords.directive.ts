import {Directive} from '@angular/core';
import {AbstractControl, FormGroup, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn} from '@angular/forms';

@Directive({
  selector: '[appSamePasswords]',
  providers: [{provide: NG_VALIDATORS, useExisting: SamePasswordsDirective, multi: true}]
})
export class SamePasswordsDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors {
    return passwordsMatch(control);
  }
}

export const passwordsMatch: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const password = control.get('password');
  const passwordRepeat = control.get('passwordRepeat');

  return password && passwordRepeat && password.value === passwordRepeat.value ? null : {'differentPasswords': true};
};
