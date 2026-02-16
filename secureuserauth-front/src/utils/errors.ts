import axios, { AxiosError } from "axios";

export interface ApiError {
  message: string;
}

export type ApiAxiosError = AxiosError<ApiError>;

export function getAxiosErrorMessage(error: ApiAxiosError) {
  if (error.response) {
    return (
      error.response.data?.message ||
      `Erro ${error.response.status}`
    );
  }

  if (error.code === "ECONNABORTED") {
    return "Connection time expired.";
  }

  if (error.request) {
    return "We were unable to connect to the server. The backend is down.";
  }

  return "Unexpected error.";
}
