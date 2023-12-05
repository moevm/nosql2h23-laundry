import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../../store";

interface Action {
    id: number,
    login: string,
    name: string,
    role: string
}


interface AuthState {
    authorized: boolean,
    id: number,
    login: string,
    name: string,
    role: string
}

const initialState: AuthState = {
    authorized: false,
    id: -1,
    login: "",
    name: "",
    role: ""
}

export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        resetUser: (state) => {
            state.authorized = false;
            state.id = -1;
            state.login = "";
            state.name = "";
            state.role = "";
        },
        setUser: (state, action: PayloadAction<Action>) => {
            state.authorized = true;
            state.id = action.payload.id;
            state.login = action.payload.login;
            state.name = action.payload.name;
            state.role = action.payload.role;
        }
    }
})

export const {resetUser, setUser} = authSlice.actions

// export type AuthStateType = AuthState;

export const selectAuth = (state: RootState) => state.auth

export default authSlice.reducer