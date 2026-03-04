import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { BookingResponse } from "../types/BookingResponse";
import type { BookingPayload } from "../types/BookingPayload";


export const bookingApi = createApi({
  reducerPath: "bookingApiSlice",
  tagTypes: ["Booking"],

  baseQuery: fetchBaseQuery({
    baseUrl: import.meta.env.VITE_API_BASE_URL,
  }),
  endpoints: (builder) => ({

    addBooking: builder.mutation<BookingResponse, Partial<BookingPayload>>({
    
      query: (body) => ({
        url: "/booking",
        method: "POST",
        body: body,
        headers: {
          "Content-Type": "application/json",
        }
      }),
      invalidatesTags: ["Booking"],
    }),

    // getBookingById: builder.query<BookingResponse, number>({
    //   query: (id) => `/${id}`,
    //   providesTags: (result, error, id) => [{ type: "Booking", id }],
    // }),
  }),
});

export const { useAddBookingMutation } = bookingApi;
