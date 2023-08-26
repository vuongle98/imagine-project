import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { User } from '@shared/models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService extends AbstractService {
  apiEndpoint = {
    userProfile: 'api/user/profile',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  getProfile(username: string): Observable<User> {
    return this.get(this.apiEndpoint.userProfile, {
      queryParams: { username },
    });
  }
}
