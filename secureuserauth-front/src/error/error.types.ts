export type UserRegisterError = {
    email?: string;
    name?: string;
    password?: string;
}

export function isUserRegisterError(obj: unknown): obj is UserRegisterError {
    if (!obj || typeof obj !== "object") return false;

    // Tenta acessar response.data com segurança
    const maybeErr = (obj as { response?: { data?: unknown } }).response?.data ?? obj;

    if (!maybeErr || typeof maybeErr !== "object") return false;

    // Checa se as propriedades existem
    return "email" in maybeErr || "name" in maybeErr || "password" in maybeErr;
}
