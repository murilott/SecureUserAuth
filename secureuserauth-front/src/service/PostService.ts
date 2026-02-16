import { api } from "./interceptor";
import type { CreatePostRequestDto } from "../dto/request/CreatePostRequestDto";
import type { PostDto } from "../types/PostDto";

export const listPosts = async (): Promise<PostDto[]> => {
  const response = await api.get<PostDto[]>("/api/v1/post");

  return response.data;
};

export const createPost = async (
    createPostRequest: CreatePostRequestDto
): Promise<PostDto> => {
    const response = await api.post<PostDto>(
        `/api/v1/post`,
        createPostRequest
    );

    return response.data;
};
