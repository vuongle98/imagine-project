import { QuizCategory, QuestionType, QuizLevel } from '../models/quiz';

export const listQuestionType: any[] = [
  {
    name: 'Tất cả',
    value: ''
  },
  {
    name: 'Chọn nhiều',
    value: QuestionType[QuestionType.MULTIPLE],
  },
  {
    name: 'Nhập văn bản',
    value: QuestionType[QuestionType.TEXT_BOX],
  },
  {
    name: 'Yes no',
    value: QuestionType[QuestionType.YES_NO],
  }
];

export const listQuizCategory: any[] = [
  {
    name: 'Tất cả',
    value: ''
  },
  {
    name: 'Chung',
    value: QuizCategory[QuizCategory.GENERAL],
  },
  {
    name: 'IT',
    value: QuizCategory[QuizCategory.IT],
  },
  {
    name: 'Địa lý',
    value: QuizCategory[QuizCategory.COUNTRY],
  },
];


export const listDifficultLevel: any[] = [
  {
    name: 'Cấp 1',
    value: 'EASY',
  },
  {
    name: 'Cấp 2',
    value: 'MEDIUM',
  },
  {
    name: 'Cấp 3',
    value: 'HARD',
  }
]

export const listQuizLevel: any[] = [
  {
    name: 'Dễ',
    value: QuizLevel[QuizLevel.EASY],
  },
  {
    name: 'Trung bình',
    value: QuizLevel[QuizLevel.MEDIUM],
  },
  {
    name: 'Khó',
    value: QuizLevel[QuizLevel.HARD],
  }
]
