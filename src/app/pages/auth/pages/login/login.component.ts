import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
    private router: Router
  ) {
    this.initLoginForm();
  }

  initLoginForm() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      remember: [''],
    });
  }

  login() {
    console.log(this.loginForm.value);
    this.authStore
      .login(this.loginForm.value as LoginPayload)
      .subscribe(() => this.router.navigateByUrl('/'));
  }
}
