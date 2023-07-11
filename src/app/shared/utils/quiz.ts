import { QuestionCategory, QuestionType } from '../models/quiz';

export const listQuestionType: any[] = [
  {
    name: 'Chọn nhiều',
    value: QuestionType.MULTIPLE,
  },
  {
    name: 'Nhập văn bản',
    value: QuestionType.TEXT_BOX,
  },
  {
    name: 'Yes no',
    value: QuestionType.YES_NO,
  },
];


export const listQuestionCategory: any[] = [
  {
    name: 'Chung',
    value: QuestionCategory.GENERAL,
  },
  {
    name: 'IT',
    value: QuestionCategory.IT,
  },
  {
    name: 'Địa lý',
    value: QuestionCategory.COUNTRY,
  },
];
