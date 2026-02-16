import axios from "axios";

const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export const api = axios.create({
    baseURL: BASE_URL,
    timeout: 5000,
    withCredentials: true,
});

const refreshToken = () =>
    api.post("/auth/refresh");

let isRefreshing = false;
let refreshSubscribers: ((token: string) => void)[] = [];

const onRefreshed = (token: string) => {
    refreshSubscribers.forEach(cb => cb(token));
    refreshSubscribers = [];
};


api.interceptors.request.use(config => {
    if (!config.url?.includes("/auth/refresh")) {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
    }
    return config;
});

api.interceptors.response.use(
    res => res,
    async error => {
        const originalRequest = error.config;

        if (
            error.response?.status === 401 &&
            !originalRequest._retry &&
            !originalRequest.url?.includes("/auth/refresh")
        ) {
            originalRequest._retry = true;

            if (isRefreshing) {
                // Espera o refresh atual terminar
                return new Promise((resolve, reject) => {
                    refreshSubscribers.push((token: string) => {
                        originalRequest.headers.Authorization = `Bearer ${token}`;
                        resolve(api(originalRequest));
                    });
                });
            }

            isRefreshing = true;

            try {
                const response = await refreshToken();
                localStorage.setItem("token", response.data.token);
                originalRequest.headers.Authorization = `Bearer ${response.data.token}`;

                onRefreshed(response.data.token); // Notifica outros requests
                return api(originalRequest);
            } catch (err) {
                localStorage.removeItem("token");
                return Promise.reject(err);
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(error);
    }
);

