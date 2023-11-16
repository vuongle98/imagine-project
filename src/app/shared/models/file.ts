import { BaseQueryParam } from "./utils";

export type FileInfo = {
  id: string;
  fileName: string;
  extension: string;
  size: number;
}

export type FileQuery = {
  likeName?: string;
  type?: string;
} & BaseQueryParam;
