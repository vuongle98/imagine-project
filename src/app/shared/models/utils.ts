export type Pageable<T> = {
  content: T;
  size: number;
  page: number;
  last: boolean;
  first: boolean;
}
