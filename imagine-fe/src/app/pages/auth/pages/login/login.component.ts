import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { StartupService } from '@core/bootstrap/startup.service';
import { LoadingService } from '@shared/components/loading/loading.service';
import { tap } from 'rxjs';
import { LoginPayload } from 'src/app/shared/models/user';
import { AuthStore } from 'src/app/shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authStore: AuthStore,
    private router: Router,
    private startupService: StartupService,
    private loadingService: LoadingService
  ) {
    this.initLoginForm();
  }

  initLoginForm() {
    this.loginForm = this.fb.group({
      username: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^[a-zA-Z0-9_]{3,16}$/),
        ]),
      ],
      password: ['', Validators.required],
      // remember: [''],
    });
  }

  login() {
    this.loadingService
      .showLoaderUntilCompleted(this.authStore.login(this.loginForm.value))
      .pipe(tap(() => this.startupService.load()))
      .subscribe(() => this.router.navigateByUrl('/'));
  }
}
