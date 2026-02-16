import type { UserDto } from "../types/UserDto";
import type { RegisterResponseDto } from "../dto/response/RegisterResponseDto";
import { api } from "./interceptor";

export const listUsers = async (): Promise<UserDto[]> => {
  const response = await api.get<UserDto[]>("/api/v1/user");

  return response.data;
};

export const createUser = async (registerResponse: RegisterResponseDto) => api.post<UserDto>(`/api/v1/user/register`, registerResponse);
