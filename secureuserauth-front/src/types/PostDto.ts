import type { Role } from "../enums/Role";

export interface PostDto {
    id: number | null,
    authorName: string,
    authorId: number,
    title: string,
    content: string,
    createdAt: Date,
}