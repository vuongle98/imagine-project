import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '@shared/models/user';
import { ChattingService } from '@shared/services/common/chatting.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { ChatService } from '@shared/services/rest-api/chat/chat.service';
import { UserService } from '@shared/services/rest-api/user/user.service';
import { Observable, combineLatest, iif, map, of, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent {
  viewProfileUser$!: Observable<User>;

  pendingFriend$!: Observable<User[]>;

  acceptedFriend$!: Observable<User[]>;

  requestedFriend$!: Observable<User[]>;

  constructor(
    public authStore: AuthStore,
    private activateRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private chatService: ChatService,
    private chattingService: ChattingService
  ) {
    this.viewProfileUser$ = this.activateRoute.data.pipe(
      switchMap((data) =>
        iif(() => !!data['user'], of(data['user']), authStore.user$)
      )
    );

    this.pendingFriend$ = this.viewProfileUser$.pipe(
      map(
        (user) =>
          user.friends?.filter((f) => f.friendStatus === 'PENDING') as User[]
      )
    );

    this.acceptedFriend$ = this.viewProfileUser$.pipe(
      map(
        (user) =>
          user.friends?.filter((f) => f.friendStatus === 'ACCEPTED') as User[]
      )
    );

    this.requestedFriend$ = this.viewProfileUser$.pipe(
      map(
        (user) =>
          user.friends?.filter((f) => f.friendStatus === 'REQUESTED') as User[]
      )
    );
  }

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

  addFriend(friendId: string) {
    this.userService.addFriend(friendId).subscribe();
  }

  logout() {
    this.authStore.logout();
    this.router.navigateByUrl('/auth/login');
  }

  isViewOtherProfile(): Observable<boolean> {
    return combineLatest([this.authStore.user$, this.viewProfileUser$]).pipe(
      map(([user, viewProfileUser]) => {
        return user.id !== viewProfileUser.id;
      })
    );
  }

  alreadyFriend(friendId: string): Observable<boolean> {
    return this.checkFriendshipStatus(friendId, 'ACCEPTED');
  }

  isRequestedFriend(friendId: string): Observable<boolean> {
    return this.checkFriendshipStatus(friendId, 'REQUESTED');
  }

  isPendingFriend(friendId: string): Observable<boolean> {
    return this.checkFriendshipStatus(friendId, 'PENDING');
  }

  isDeclineFriend(friendId: string): Observable<boolean> {
    return this.checkFriendshipStatus(friendId, 'REJECTED');
  }

  isRejectedRequestFriend(friendId: string): Observable<boolean> {
    return this.checkFriendshipStatus(friendId, 'REJECTED_REQUEST');
  }

  alreadyOrRequestedFriend(friendId: string): Observable<boolean> {
    return combineLatest([
      this.alreadyFriend(friendId),
      this.isRequestedFriend(friendId),
    ]).pipe(
      map(([already, requested]) => {
        return already || requested;
      })
    );
  }

  acceptFriend(friendId: string) {
    this.userService.acceptFriend(friendId).subscribe();
  }

  declineFriend(friendId: string) {
    this.userService.declineFriend(friendId).subscribe();
  }

  openChat(friendId: string, friendName: string) {
    this.authStore.user$
      .pipe(
        switchMap((user) => {
          const payload = {
            addMemberIds: [friendId],
            name: user.fullName + ' and ' + friendName,
            type: 'PRIVATE',
          };
          return this.chatService.createConversation(payload).pipe(
            tap((chatInfo) => {
              console.log(chatInfo);
              this.chattingService.openChat(chatInfo);
            })
          );
        })
      )

      .subscribe();
  }
}
