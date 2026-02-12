import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { User } from "../types/User";

export const userApi = createApi({
  reducerPath: "userApiSlice",
  tagTypes: ["User"],
  baseQuery: fetchBaseQuery({
    baseUrl: "https://dummyjson.com/users",
    prepareHeaders: (headers) => {
      headers.set("Content-Type", "application/json");
      headers.set("Access-Control-Allow-Origin", "*");
      return headers;
    },
  }),
  endpoints: (builder) => ({
    getAllUsers: builder.query<{ users: User[] }, void>({
      query: () => "",
      transformResponse: (response: any): { users: User[] } => {
        
        return {
          users: response.users.map(
            ({
              id,
              firstName,
              lastName,
              email,
              phone,
              image,
              age,
            }: {
              id: number;
              firstName: string;
              lastName: string;
              email: string;
              phone: string;
              image: string;
              age: number;
            }) => ({
              id,
              firstName,
              lastName,
              email,
              phone,
              avatar: image,
              age,
            }),
          ),
        };
      },
      keepUnusedDataFor: 300,
      providesTags: (result) =>
        result
          ? [
              ...result.users.map(({ id }) => ({ type: "User" as const, id })),
              "User",
            ]
          : ["User"],
    }),

    addUser: builder.mutation<User, Partial<User>>({
    
      query: (newUser) => ({
        url: "",
        method: "POST",
        body: newUser,
      }),
      invalidatesTags: ["User"],
    }),

    getUserById: builder.query<User, number>({
      query: (id) => `/${id}`,
      transformResponse: (response: any): User => {
        const { id, firstName, lastName, email, phone, image, age } = response;
        return {
          id,
          firstName,
          lastName,
          email,
          phone,
          avatar: image,
          age,
        };
      },
      providesTags: (result, error, id) => [{ type: "User", id }],
    }),
  }),
});

export const { useGetAllUsersQuery, useAddUserMutation, useGetUserByIdQuery } =
  userApi;
