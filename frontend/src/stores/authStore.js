import {defineStore} from 'pinia';
import {jwtDecode} from 'jwt-decode';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('jwtToken') || null,
        role: null
    }),
    actions: {
        setToken(token) {
            this.token = token;
            localStorage.setItem('jwtToken', token);
            this.decodeAndSetRole(token);
        },

        tryAutoLogin() {
            if (this.token) {
                this.decodeAndSetRole(this.token);
            }
        },


        decodeAndSetRole(token) {
            try {
                const decoded = jwtDecode(token);

                if (decoded.exp * 1000 < Date.now()) {
                    console.error('Token has expired.');
                    this.clearAuth();
                    return;
                }

                this.role = decoded.role ? decoded.role.toUpperCase() : null;
                console.log('Set role in authStore:', this.role);

                if (!this.role) {
                    console.error('Role not found in token! Defaulting to ENDUSER');
                    this.role = 'ENDUSER';
                }
            } catch (error) {
                console.error('Error decoding JWT:', error);
                this.role = 'ENDUSER';
                this.clearAuth();
            }
        },

        clearAuth() {
            this.token = null;
            this.role = null;
            localStorage.removeItem('jwtToken');
        }
    }
});