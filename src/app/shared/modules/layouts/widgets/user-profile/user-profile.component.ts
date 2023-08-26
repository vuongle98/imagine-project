import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent {
  constructor(
    public auth: AuthStore,
    private router: Router,

  ) { }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('/auth/login');
  }
}
