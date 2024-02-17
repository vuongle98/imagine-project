import { User } from './user';
import { BaseQueryParam } from './utils';

export type ChatMessage = {
  id: string;
  content: string;
  conversation: Conversation;
  sender: User;
  replyTo: string;
  fileId: string;
  createdAt: Date;
};

export type Conversation = {
  id: string;
  title: string;
  participants?: string[];
  createdAt?: Date;
  deleted?: boolean;
};

export type ChatMessageQueryParam = {
  likeContent?: string;
  conversationId?: string;
} & BaseQueryParam;
