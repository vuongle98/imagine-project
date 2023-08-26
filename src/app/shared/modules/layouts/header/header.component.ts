import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  constructor(
    public auth: AuthStore,
    private router: Router,

  ) { }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('/auth/login');
  }
}
