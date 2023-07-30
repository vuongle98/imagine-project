export type User = {
  id?: string;
  fullName: string;
  username: string;
  email: string;
  roles: string[];
};

export type LoginPayload = {
  username: string;
  password: string;
};

export type TokenResponse = User & {
  token: string;
  type: string;
};

export type RegisterPayload = User & LoginPayload;
