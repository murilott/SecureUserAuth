import type { JSX } from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "./useAuth";

export function RoleRoute({
  children,
  role
}: {
  children: JSX.Element;
  role: string;
}) {
  const { user, isAuthenticated } = useAuth();

    console.log(isAuthenticated);
  
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }
    
    console.log(user?.roles, role);
    console.log(user?.roles.some(r => r === role));
    
    if (user?.roles.some(r => r === role)) {
        return children
    } else {
        return <Navigate to="/403" />;
    }
}
