import { User } from './user';
import { BaseQueryParam } from './utils';

export type Quiz = {
  id: string;
  listQuestionId: string[];
  questions: Question[];
  title: string;
  imagePath: string;
  description: string;
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
  category: QuestionCategory;
};

export type Answer = {
  id: string;
  answer: string;
};

export enum QuestionType {
  YES_NO,
  MULTIPLE,
  TEXT_BOX,
}

export type CheckAnswer = {
  questionId: string;
  answerIds: string[];
};

export type CheckAnswerResponse = {
  totalAnswers: number;
  correctAnswers: number;
};

export type QuizQueryParam = {
  id?: string;
  likeTitle?: string;
  likeQuestion?: string;
} & BaseQueryParam;

export type QuestionQueryParam = {
  id?: string;
  likeAnswer?: string;
  difficultlyLevel?: number;
  category?: QuestionCategory;
  type?: QuestionType;
} & BaseQueryParam;

export enum QuestionCategory {
  GENERAL,
  IT,
  COUNTRY,
}
