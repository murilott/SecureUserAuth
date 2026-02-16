// import type { Role } from "../enums/Role";
// import type { UserDto } from "../types/UserDto";

// type JwtPayload = {
//   sub: number;
//   // name: string;
//   // email: string;
//   // roles: Role[];
//   exp: number;
//   iat: number;
// };

// export function decodeToken(token: string): UserDto {
//   const payload = JSON.parse(atob(token.split(".")[1])) as JwtPayload;

//   return {
//     id: payload.sub,
//     // name: payload.name,
//     // email: payload.email,
//     // roles: payload.roles ?? []
//   };
// }
