import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { UIConfig } from "../types/Config";



export const configApi = createApi({
  reducerPath: "configApiSlice",
  tagTypes: ["Config"],

  baseQuery: fetchBaseQuery({
    baseUrl: import.meta.env.VITE_API_BASE_URL,
  }),
  endpoints: (builder) => ({

    getConfig: builder.query<UIConfig, void>({
    
      query: () => ({
        url: "/config",
        method: "GET",
      })
    }),

  }),
});

export const { useGetConfigQuery } = configApi;
