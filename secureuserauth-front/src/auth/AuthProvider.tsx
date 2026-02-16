import { useEffect, useState } from "react";
import { AuthContext } from "./AuthContext";
import type { UserDto } from "../types/UserDto";
import { getMe } from "../service/AuthService";

export function AuthProvider({ children }: { children: React.ReactNode }) {
    const [user, setUser] = useState<UserDto | null>(null);

    const [initialized, setInitialized] = useState(() => {
        return !localStorage.getItem("token");
    });

    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem("token");
            if (!token) {
                setInitialized(true);
                return;
            }

            try {
                const me = await getMe();
                setUser(me);
            } catch (error) {
                console.log("Interceptor /me error", error);
                localStorage.removeItem("token");
                setUser(null);
            } finally {
                setInitialized(true);
            }
        };

        fetchUser();
    }, []);

    if (!initialized) return <div><p>Loading...</p></div>;

    const loginToken = async (token: string) => {
        localStorage.setItem("token", token);
        const me = await getMe();
        setUser(me);
    };

    const logoutToken = () => {
        localStorage.removeItem("token");
        setUser(null);
    };


    return (
        <AuthContext.Provider
            value={{
                user,
                isAdmin: !!user?.roles?.some(r => r === "ADMIN"),
                isModerator: !!user?.roles?.some(r => r === "MODERATOR"),
                isAuthenticated: !!user,
                loginToken,
                logoutToken
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}
