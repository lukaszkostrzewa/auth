import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  private login: string;

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.login = localStorage.getItem('login');
  }

  logout() {
    localStorage.removeItem('login');
    this.router.navigate(['/login']);
  }
}
