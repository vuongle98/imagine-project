import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  registerForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authStore: AuthStore,
    private router: Router
  ) {
    this.initLoginForm();
  }

  initLoginForm() {
    this.registerForm = this.fb.group({
      username: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^[a-zA-Z0-9_]{3,16}$/),
        ]),
      ],
      password: ['', Validators.required],
      rePassword: ['', Validators.required],
    });
  }

  register() {}
}
