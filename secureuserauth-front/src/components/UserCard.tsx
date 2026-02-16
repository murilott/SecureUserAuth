import React from 'react'
import type { Role } from '../enums/Role';
import type { UserDto } from '../types/UserDto';
import "../style/usercard.css"

interface UserCardProps {
    user: UserDto | null;
}

function UserCard({ user }: UserCardProps) {
    if (!user) { return }

    return (
        <div key={user.id} className='userCard'>
            <p><strong>Id</strong> {user.id}</p>
            <p><strong>Name</strong> {user.name}</p>
            <p><strong>Email</strong> {user.email}</p>
            <p><strong>Roles</strong> {user.roles.join(" | ")}</p>
        </div>
    )
}

export default UserCard