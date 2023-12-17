import { User } from "./user";

export type ChatMessage = {
  id: string;
  content: string;
  conversationId: string;
  sender: User;
  replyTo: string;
  fileId: string;
  timeStamp: Date;
}

export type Conversation = {
  id: string;
  title: string;
  participants?: string[];
  timeStamp?: Date;
  deleted?: boolean;
}
