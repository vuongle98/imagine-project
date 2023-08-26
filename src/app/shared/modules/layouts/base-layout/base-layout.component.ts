import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ChattingService } from '@shared/services/common/chatting.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { fromEvent } from 'rxjs';

@Component({
  selector: 'app-base-layout',
  templateUrl: './base-layout.component.html',
  styleUrls: ['./base-layout.component.scss']
})
export class BaseLayoutComponent {

  isCollapsed = false;

  constructor(
    public auth: AuthStore,
    private router: Router,
    public chattingService: ChattingService
  ) {}


  windowScrolled = false;

  ngOnInit(): void {
    fromEvent(document, 'scroll').subscribe(() => {
      this.windowScrolled = window.scrollY > 100;
    });

    sessionStorage.clear();
  }

  scrollToTop(): void {
    window.scrollTo(0, 0);
  }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('/auth/login');
  }

}
