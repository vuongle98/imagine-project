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
    user: 'api/user',
    addFriend: 'api/user/add-friend',
    acceptFriend: 'api/user/accept-friend',
    declineFriend: 'api/user/decline-friend',
    removeFriend: 'api/user/remove-friend',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  getProfile(username: string): Observable<User> {
    return this.get(this.apiEndpoint.userProfile, {
      queryParams: { username },
    });
  }

  addFriend(friendId: string): Observable<User> {
    return this.put(this.apiEndpoint.addFriend, {
      queryParams: { 'friend-id': friendId },
    });
  }

  acceptFriend(friendId: string): Observable<User> {
    return this.put(this.apiEndpoint.acceptFriend, {
      queryParams: { 'friend-id': friendId },
    });
  }

  declineFriend(friendId: string): Observable<User> {
    return this.put(this.apiEndpoint.declineFriend, {
      queryParams: { 'friend-id': friendId },
    });
  }

  removeFriend(friendId: string): Observable<User> {
    return this.put(this.apiEndpoint.removeFriend, {
      queryParams: { 'friend-id': friendId },
    });
  }
}
