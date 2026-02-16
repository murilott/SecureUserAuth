import type { LoginResponseDto } from "../dto/response/LoginResponseDto";
import type { TokenResponseDto } from "../dto/response/TokenResponseDto";
import type { UserDto } from "../types/UserDto";
import { api } from "./interceptor";

export const login = async (loginResponse: LoginResponseDto) => api.post<TokenResponseDto>(`/auth/login`, loginResponse);

// export const refresh = async () => api.post<TokenResponseDto>(`/auth/refresh`);

export const getMe = async ():Promise<UserDto> =>  {
  const response = await api.get<UserDto>("/auth/me");
  return response.data;
};
