import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  Output,
} from '@angular/core';
import { BehaviorSubject, Subject, takeUntil } from 'rxjs';
import { Question, QuestionType } from 'src/app/shared/models/quiz';
import { CountdownService } from 'src/app/shared/services/common/countdown.service';

@Component({
  selector: 'app-question-item-custom',
  templateUrl: './question-item-custom.component.html',
  styleUrls: ['./question-item-custom.component.scss'],
})
export class QuestionItemCustomComponent implements OnDestroy {
  QuestionType = QuestionType;

  @Input() question!: Question;
  @Input() index!: number;
  @Input() size!: number;
  @Output() timeOverChange = new EventEmitter<boolean>();

  displayItemIndex$ = new BehaviorSubject<number>(0);
  counter$ = new BehaviorSubject<number>(0);

  counter = 30;

  onDestroyed$ = new Subject();

  constructor(private countdownService: CountdownService) {}

  ngOnInit(): void {
    this.countdownService
      .startCountdown(this.question.countDown || 30)
      .pipe(takeUntil(this.onDestroyed$))
      .subscribe((counter) => {
        this.counter = counter;
        if (counter === 0) {
          this.timeOverChange.emit(true);
        }
      });
  }

  ngOnDestroy() {
    this.onDestroyed$.next('');
    this.onDestroyed$.complete();
  }
}
