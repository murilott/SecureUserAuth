// export type AuthUser = {
//   id: number
//   username: string;
//   roles: string[];
// };

import type { UserDto } from "../types/UserDto";

export type AuthContextType = {
  user: UserDto | null;
  isAuthenticated: boolean;
  isAdmin: boolean;
  isModerator: boolean;
  loginToken: (token: string) => void;
  logoutToken: () => void;
};
