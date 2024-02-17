import { RxStompService } from './rx-stomp.service';
import { rxStompConfig } from '../../utils/rx-stomp-config';

export function rxStompServiceFactory() {
  const rxStomp = new RxStompService();

  rxStomp.configure(rxStompConfig);
  // rxStomp.activate();
  return rxStomp;
}
