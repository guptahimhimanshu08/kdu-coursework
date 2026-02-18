import { useDispatch, useSelector, type TypedUseSelectorHook } from "react-redux";
import registerReducer from "../slices/registerSlice";
import { configureStore } from "@reduxjs/toolkit";
import statusReducer from "../slices/statusSlice";
export const store = configureStore({
    reducer:{
        registration: registerReducer,
        status: statusReducer
    }
})

type RootState = ReturnType<typeof store.getState>;
type AppDispatch = typeof store.dispatch;

export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
