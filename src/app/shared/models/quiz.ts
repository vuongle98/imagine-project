import { User } from './user';

export type Quiz = {
  id: string;
  listQuestionId: string[];
  questions: Question[];
  title: string;
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
