import {Directive} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, ValidationErrors, Validator} from '@angular/forms';

@Directive({
  selector: '[appStrongPassword]',
  providers: [{provide: NG_VALIDATORS, useExisting: StrongPasswordDirective, multi: true}]
})
export class StrongPasswordDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors | null {
    return isPasswordStrong(control.value) ? null : {'weakPassword': {value: control.value}};
  }
}

function isPasswordStrong(password: string | null): boolean {
  if (!password) {
    return false;
  }
  const chars = password.split('');
  return chars.some(isUppercaseLetter) && chars.some(isLowercaseLetter) && chars.some(isNumber);
}

function isUppercaseLetter(char) {
  return char >= 'A' && char <= 'Z';
}

function isLowercaseLetter(char) {
  return char >= 'a' && char <= 'z';
}

function isNumber(char) {
  return char >= '0' && char <= '9';
}
