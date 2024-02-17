import { Injectable } from '@angular/core';
import {
  Observable,
  Subject,
  map,
  of,
  switchMap,
  takeUntil,
  takeWhile,
  tap,
  timer,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CountdownService {
  constructor() {
  }

  startCountdown(duration: number = 30): Observable<number> {
    return timer(1000, 1000).pipe(
      takeWhile(() => duration > 0),
      tap(() => {
        duration -= 1;
      }),
      map(() => duration)
    );
  }
}
