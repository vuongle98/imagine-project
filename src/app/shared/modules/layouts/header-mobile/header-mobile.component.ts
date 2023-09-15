import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-header-mobile',
  templateUrl: './header-mobile.component.html',
  styleUrls: ['./header-mobile.component.scss']
})
export class HeaderMobileComponent {


  constructor(public auth: AuthStore, private router: Router) {}

  ngOnInit(): void {
  }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('/auth/login');
  }

}
