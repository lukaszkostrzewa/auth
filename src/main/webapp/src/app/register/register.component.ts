import {Component} from '@angular/core';
import {UserService} from '../user.service';
import {User} from '../user';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  private login: string;
  private password: string;

  constructor(private userService: UserService, private router: Router) {
  }

  onSubmit() {
    this.userService.registerUser(new User(this.login, this.password))
      .subscribe((response: User) => {
        localStorage.setItem('login', response.login);
        this.router.navigate(['/account']);
      });
  }

  alphaNumeric(event) {
    return /[a-z0-9]/i.test(event.key);
  }
}
