import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(public auth: AuthStore, private router: Router) {}

  ngOnInit(): void {}

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('/auth/login');
  }
}
