import { RxStompService } from './rx-stomp.service';
import { rxStompConfig } from '../../utils/rx-stomp-config';
import { inject } from '@angular/core';
import { AuthStore } from '../rest-api/auth/auth.store';

export function rxStompServiceFactory() {
  const rxStomp = new RxStompService();
  const authStore = inject(AuthStore);

  if (rxStompConfig.connectHeaders && authStore.token.length > 0)
    rxStompConfig.connectHeaders['Authorization'] = authStore.token;

  console.log(rxStompConfig);

  rxStomp.configure(rxStompConfig);
  rxStomp.activate();
  return rxStomp;
}
