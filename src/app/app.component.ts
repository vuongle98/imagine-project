import { Component, OnInit } from '@angular/core';
import { AuthStore } from './shared/services/rest-api/auth/auth.store';
import { Router } from '@angular/router';
import { fromEvent } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  isCollapsed = false;

  constructor(public auth: AuthStore, private router: Router) {}

  windowScrolled = false;

  ngOnInit(): void {
    fromEvent(document, 'scroll').subscribe(() => {
      this.windowScrolled = window.scrollY > 100;
    });
  }

  scrollToTop(): void {
    window.scrollTo(0, 0);
  }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('/');
  }
}
