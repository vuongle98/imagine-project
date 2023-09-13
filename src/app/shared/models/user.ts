export type User = {
  id: string;
  fullName: string;
  username: string;
  email: string;
  roles: string[];
  token?: string;
  friends?: User[];
  friendStatus?: string;
};

export type Friendship = {
  id: string;
  status: string;
  updateTime: Date;
}

export type LoginPayload = {
  username: string;
  password: string;
};

export type TokenResponse = {
  token: string;
  type: string;
  user: User;
};

export type RegisterPayload = User & LoginPayload;
