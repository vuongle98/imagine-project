import { BaseQueryParam } from './utils';

export type User = {
  id: string;
  fullName?: string;
  username?: string;
  email?: string;
  roles?: string[];
  token?: string;
  friends?: User[];
  friendships?: Friendship[];
  friendStatus?: string;
  locked?: boolean;
  enabled?: boolean
};

export type Friendship = {
  id: string;
  status: string;
  updateFrom: Date;
  friendFrom: Date;
  friend: User;
  user: User;
};

export type LoginPayload = {
  username: string;
  password: string;
};

export type TokenResponse = {
  token: string;
  type: string;
  user: User;
};

export type UserQueryParam = {
  username?: string;
  email?: string;
  fullName?: string;
  id?: string;
} & BaseQueryParam;

export type RegisterPayload = User & LoginPayload;
