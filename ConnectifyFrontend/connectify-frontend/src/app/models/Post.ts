export interface Post {
    id: number;
    author: {
      id: number;
      username: string;
      email: string;
      authorities: string[]
    };
    content: string;
    timestamp: string;
    comments: { id: number }[];
  }