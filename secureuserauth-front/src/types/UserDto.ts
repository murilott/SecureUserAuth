import type { Role } from "../enums/Role";

export interface UserDto {
    id: number | null,
    name: string,
    email: string,
    roles: Role[],
}