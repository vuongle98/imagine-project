export type Pageable<T> = {
  content: T;
  size: number;
  number: number;
  last: boolean;
  first: boolean;
  empty: boolean;
  totalElements: number;
  totalPages: number;
}

export type BaseQueryParam = {
  page?: number;
  size?: number;
  sort?: string;
}

export type MessageResponse = {
  title: string;
  message: string;
}
