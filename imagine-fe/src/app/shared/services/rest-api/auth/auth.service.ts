import { Injectable } from '@angular/core';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import {
  LoginPayload,
  RegisterPayload,
  TokenResponse,
  User,
} from 'src/app/shared/models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends AbstractService {
  apiEndpoint = {
    auth: 'api/auth',
    token: 'api/auth/token',
    logout: 'api/auth/logout',
    register: 'api/auth/register',
    verify: 'api/auth/verify',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  getToken(payload: LoginPayload): Observable<TokenResponse> {
    return this.post(this.apiEndpoint.token, {
      requestBody: { data: payload, type: 'application/json' },
    });
  }

  register(payload: RegisterPayload) {
    return this.post(this.apiEndpoint.register, {
      requestBody: { data: payload, type: 'application/json' },
    });
  }

  verifyUser(): Observable<User> {
    return this.get(this.apiEndpoint.verify, {});
  }
}
