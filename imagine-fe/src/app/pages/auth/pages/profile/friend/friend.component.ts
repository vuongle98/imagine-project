import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Friendship } from '@shared/models/user';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { UserService } from '@shared/services/rest-api/user/user.service';
import { Observable, map, tap } from 'rxjs';

@Component({
  selector: 'app-friend',
  templateUrl: './friend.component.html',
  styleUrls: ['./friend.component.scss'],
})
export class FriendComponent {
  @Input() friendships: Friendship[] = [];
  @Input() friendStatus!: string;
  @Output() dataChange = new EventEmitter();

  constructor(private authStore: AuthStore, private userService: UserService) {}

  checkFriendshipStatus(
    friendId: string,
    friendStatus: string
  ): Observable<boolean> {
    return this.authStore.user$.pipe(
      map(
        (user) =>
          user.friends?.findIndex(
            (f) => f.id === friendId && f.friendStatus === friendStatus
          ) !== -1
      )
    );
  }

  friendshipStatus(friendStatus: string): string {
    switch (friendStatus) {
      case 'PENDING':
        return 'pending';
      case 'ACCEPTED':
        return 'accepted';
      case 'REQUESTED':
        return 'requested';
      default:
        return '';
    }
  }

  acceptFriend(friendId: string) {
    this.userService
      .acceptFriend(friendId)
      .pipe(tap(() => this.dataChange.emit()))
      .subscribe();
  }

  declineFriend(friendId: string) {
    this.userService
      .declineFriend(friendId)
      .pipe(tap(() => this.dataChange.emit()))
      .subscribe();
  }

  removeFriend(friendId: string) {
    this.userService
      .removeFriend(friendId)
      .pipe(tap(() => this.dataChange.emit()))
      .subscribe();
  }
}
