import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Friendship, User } from '@shared/models/user';
import { ChattingService } from '@shared/services/common/chatting.service';
import { OnDestroyService } from '@shared/services/common/on-destroyed.service';
import { AuthStore } from '@shared/services/rest-api/auth/auth.store';
import { ChatService } from '@shared/services/rest-api/chat/chat.service';
import { UserService } from '@shared/services/rest-api/user/user.service';
import {
  Observable,
  Subject,
  combineLatest,
  iif,
  map,
  of,
  shareReplay,
  switchMap,
  take,
  takeUntil,
  tap,
} from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [OnDestroyService],
})
export class ProfileComponent implements OnInit {
  viewProfileUser$!: Observable<User>;

  pendingFriend$!: Observable<Friendship[]>;

  acceptedFriend$!: Observable<Friendship[]>;

  requestedFriend$!: Observable<Friendship[]>;

  dataChange = new Subject();

  constructor(
    public authStore: AuthStore,
    private activateRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private chatService: ChatService,
    private chattingService: ChattingService,
    private onDestroyService: OnDestroyService
  ) {
    this.loadUserProfile();
  }

  ngOnInit(): void {
    this.dataChange
      .pipe(
        tap(() => this.loadUserProfile()),
        takeUntil(this.onDestroyService)
      )
      .subscribe();
  }

  loadUserProfile() {
    this.viewProfileUser$ = this.activateRoute.paramMap.pipe(
      map((param) => param.get('username')!),
      switchMap((username: string) => {
        return this.userService.getProfile(username);
      }),
      switchMap((user: User) =>
        iif(() => !!user, of(user), this.authStore.user$)
      ),
      shareReplay(),
      takeUntil(this.onDestroyService)
    );

    this.pendingFriend$ = this.viewProfileUser$.pipe(
      map(
        (user) =>
          user.friendships?.filter(
            (f) => f.status === 'PENDING'
          ) as Friendship[]
      )
    );

    this.acceptedFriend$ = this.viewProfileUser$.pipe(
      map(
        (user) =>
          user.friendships?.filter(
            (f) => f.status === 'ACCEPTED'
          ) as Friendship[]
      )
    );

    this.requestedFriend$ = this.viewProfileUser$.pipe(
      map(
        (user) =>
          user.friendships?.filter(
            (f) => f.status === 'REQUESTED'
          ) as Friendship[]
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
          user.friendships?.findIndex(
            (f) => f.friend.id === friendId && f.status === friendStatus
          ) !== -1
      )
    );
  }

  addFriend(friendId: string) {
    this.userService
      .addFriend(friendId)
      .pipe(switchMap(() => this.authStore.verifyUser()))
      .subscribe();
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
    this.userService
      .acceptFriend(friendId)
      .pipe(switchMap(() => this.authStore.verifyUser()))
      .subscribe();
  }

  declineFriend(friendId: string) {
    this.userService
      .declineFriend(friendId)
      .pipe(switchMap(() => this.authStore.verifyUser()))
      .subscribe();
  }

  openChat(friendId: string, friendName: string) {
    this.authStore.user$
      .pipe(
        switchMap((user) => {
          const payload = {
            addParticipants: [friendId],
            title: user.fullName + ' and ' + friendName,
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
