import { User } from './user';
import { BaseQueryParam } from './utils';

export type Quiz = {
  id: string;
  listQuestionId: string[];
  questions: Question[];
  title: string;
  imagePath: string;
  description: string;
  image: any;
  category: QuizCategory;
  level: QuizLevel;
  createdDate: Date;
  createdBy: User;
};

export type Question = {
  id: string;
  title: string;
  active: boolean;
  answers: Answer[];
  mark: boolean;
  difficultlyLevel: number;
  createdBy: User;
  createdAt: Date;
  checkValue: string;
  type: string;
  category: string;
  countDown: number;
  description: string;
  codeDescription: string;
  fileDescription: any;
};

export type Answer = {
  id: string;
  answer: string;
  correct: boolean;
};

export enum QuestionType {
  YES_NO,
  MULTIPLE,
  TEXT_BOX,
}

export type BaseCheckAnswer = {
  questionId: string;
  answerIds: string[];
};

export type CheckAnswer = BaseCheckAnswer & {
  correctAnswerIds: string[];
  question: Question;
};

export type CheckAnswerResponse = {
  totalAnswers: number;
  correctAnswers: CheckAnswer;
  numOfCorrectAnswers: number;
  answers: CheckAnswer[];
};

export type QuizQueryParam = {
  id?: string;
  likeTitle?: string;
  likeQuestion?: string;
} & BaseQueryParam;

export type QuestionQueryParam = {
  id?: string;
  likeAnswer?: string;
  likeTitle?: string;
  difficultlyLevel?: number;
  category?: QuizCategory;
  type?: QuestionType;
  mark?: boolean;
} & BaseQueryParam;

export enum QuizCategory {
  GENERAL,
  IT,
  COUNTRY,
}

export enum QuizLevel {
  HARD,
  MEDIUM,
  EASY,
}
