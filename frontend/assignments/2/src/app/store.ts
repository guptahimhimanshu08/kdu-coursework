import { configureStore } from "@reduxjs/toolkit";
import { configApi } from "../services/configApi";
import { bookingApi } from "../services/bookingApi";
import bookingDraftReducer from "../features/bookingSlice";

export const store = configureStore({
    reducer: {
        bookingDraft: bookingDraftReducer,
        [configApi.reducerPath]: configApi.reducer,
        [bookingApi.reducerPath]: bookingApi.reducer,
    },

    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(configApi.middleware, bookingApi.middleware )
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch


