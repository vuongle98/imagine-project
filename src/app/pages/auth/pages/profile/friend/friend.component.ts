import { Component, Input } from '@angular/core';
import { User } from '@shared/models/user';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { UserService } from '@shared/services/rest-api/user/user.service';
import { Observable, map } from 'rxjs';

@Component({
  selector: 'app-friend',
  templateUrl: './friend.component.html',
  styleUrls: ['./friend.component.scss'],
})
export class FriendComponent {
  @Input() friends: User[] = [];
  @Input() friendStatus!: string;

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
    this.userService.acceptFriend(friendId).subscribe();
  }

  declineFriend(friendId: string) {
    this.userService.declineFriend(friendId).subscribe();
  }

  removeFriend(friendId: string) {
    this.userService.removeFriend(friendId).subscribe();
  }
}
