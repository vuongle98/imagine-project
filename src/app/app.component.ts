import { Component } from '@angular/core';
import { AuthStore } from './shared/services/rest-api/auth/auth.store';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  isCollapsed = false;

  constructor(public auth: AuthStore, private router: Router) {}

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('/');
  }
}
