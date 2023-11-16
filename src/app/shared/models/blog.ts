import { User } from './user';
import { BaseQueryParam } from './utils';

export type Comment = {
  id: string;
  content: string;
  file: any;
  creator: User;
};

export type CommentQuery = {
  id?: string;
  inIds?: string[];
  likeContent?: string;
  categoryId?: string;
  postId?: string;
  creatorId?: string;
  getDeleted?: boolean;
};

export type Category = {
  id: string;
  name: string;
  deletedAt: string;
};

export type CategoryQuery = {
  id?: string;
  inIds?: string[];
  likeName?: string;
  getDeleted?: boolean;
} & BaseQueryParam;

export type Post = {
  id: string;
  title: string;
  description: string;
  file: any;
  featured: boolean;
  content: string;
  category: Category;
  creator: User;
  createdDate: string;
  deletedAt: string;
  comments: Comment[];
  numLikes: number;
  numComments: number;
};

export type PostQuery = {
  id?: string;
  inIds?: string[];
  likeTitle?: string;
  likeDescription?: string;
  categoryId?: string;
  inCategoryIds?: string[];
  getDeleted?: boolean;
  creatorId?: string;
} & BaseQueryParam;
