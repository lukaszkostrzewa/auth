import {Component} from '@angular/core';
import {SessionService} from '../session.service';
import {User} from '../user';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  private login: string;
  private password: string;
  private loginError = false;

  constructor(private sessionService: SessionService, private router: Router) {
  }

  onSubmit() {
    const user = new User(this.login, this.password);
    this.sessionService.login(user).subscribe(result => {
      if (result) {
        localStorage.setItem('login', this.login);
        this.router.navigate(['/account']);
      } else {
        this.loginError = true;
      }
    });
  }
}
