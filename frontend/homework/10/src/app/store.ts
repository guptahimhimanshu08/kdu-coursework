import { configureStore } from "@reduxjs/toolkit";
import { userApi } from "../services/userApi";
import userReducer from "./userSlice";
import { useDispatch, useSelector, type TypedUseSelectorHook } from "react-redux";

export const store = configureStore({
    reducer: {
        [userApi.reducerPath]: userApi.reducer,
        user: userReducer,
    },

    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(userApi.middleware)
})

type RootState = ReturnType<typeof store.getState>;
type AppDispatch = typeof store.dispatch;

export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;