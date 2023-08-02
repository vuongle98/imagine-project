import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Quiz } from 'src/app/shared/models/quiz';

@Component({
  selector: 'app-quiz-intro',
  templateUrl: './quiz-intro.component.html',
  styleUrls: ['./quiz-intro.component.scss']
})
export class QuizIntroComponent {
  @Input() quiz!: Quiz;

  @Output() startPlaying = new EventEmitter<boolean>();

  emitPlaying() {
    this.startPlaying.emit(true);
  }
}
