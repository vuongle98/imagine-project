import { Injectable } from '@angular/core';
import { rxStompConfig } from '@shared/utils/rx-stomp-config';
import { RxStomp } from '@stomp/rx-stomp';

@Injectable({
  providedIn: 'root',
})
export class RxStompService extends RxStomp {
  constructor() {
    super();
  }

  configWs(username?: string, token?: string) {
    const config = rxStompConfig;
    if (!config.connectHeaders) return;

    if (token?.startsWith('Bearer ')) {
      token = token.substring(7);
    }

    // chỉ 1 trong 2 có; token = loggedIn; username = not loggedIn
    if (username) {
      config.connectHeaders['username'] = username;
    } else if (token) {
      config.connectHeaders['Authorization'] = token;
    }

    if (this.active) {
      this.deactivate();
    }

    this.configure(config);
  }
}
