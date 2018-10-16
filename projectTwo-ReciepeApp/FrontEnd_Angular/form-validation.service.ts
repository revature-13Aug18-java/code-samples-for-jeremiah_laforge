import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormValidationService {

  constructor() { }

  public ValidateForm(AC: AbstractControl) {
    let password = AC.get('password').value;
    let confirmPassword = AC.get('testPassword').value;
    let email = AC.get('email').value;
    
    if (password != confirmPassword) {
        console.log('false');
        AC.get('testPassword').setErrors({ MatchPassword: true })
        
    } else {
        console.log('true');
        return null
    }
}
}
