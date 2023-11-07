import { BaseQueryParam } from "./utils";

export type FileInfo = {
  id: string;
  name: string;
  ext: string;
  size: number;
}

export type FileQuery = {
  likeName?: string;
  type?: string;
} & BaseQueryParam;
