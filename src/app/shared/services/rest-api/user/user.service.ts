import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AbstractService } from '../abstract-service';
import { HttpClient } from '@angular/common/http';
import { User, UserQueryParam } from '@shared/models/user';
import { Pageable } from '@shared/models/utils';

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

    adminUser: 'api/admin/user',
    adminUserWithId: 'api/admin/user/{id}',
  };

  constructor(private httpClient: HttpClient) {
    super(httpClient);
  }

  findUser(params: UserQueryParam): Observable<Pageable<User[]>> {
    return this.get(this.apiEndpoint.adminUser, {
      queryParams: params,
    });
  }

  adminCreateUser = (user: User): Observable<User> => {
    return this.post(this.apiEndpoint.adminUser, {
      requestBody: { data: user, type: 'application/json' },
    });
  };

  adminUpdateUser = (id: string, user: User): Observable<User> => {
    return this.put(this.apiEndpoint.adminUserWithId, {
      pathParams: { id },
      requestBody: { data: user, type: 'application/json' },
    });
  };

  adminDeleteUser(id: string, forever = false): Observable<void> {
    return this.delete(this.apiEndpoint.adminUserWithId, {
      pathParams: { id },
      queryParams: { 'delete-forever': forever },
    });
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
