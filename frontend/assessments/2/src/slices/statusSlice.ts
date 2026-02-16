import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { getEventById } from "../services/registrationService";
import type { StatusResponse } from "../types/StatusResponse";


interface StatusState {
  registrationId: string;
  name: string;
  email: string;
  eventName: string;
  status: "SUCCESSFULL" | "FAILED" | "QUEUED" | "";
  loading: boolean;
  error: string | null;
}

const initialState: StatusState = {
  registrationId: "",
  name: "",
  email: "",
  eventName: "",
  status: "",
  loading: false,
  error: null,
};

const getErrorMessage = (error: unknown): string =>
  error instanceof Error ? error.message : "Something went wrong";

export const fetchStatusThunk = createAsyncThunk<
  StatusResponse,
  string,
  { rejectValue: string }
>("status/fetchById", async (id, { rejectWithValue }) => {
  try {
    const response = await getEventById({ id });
    return response;
  } catch (error) {
    return rejectWithValue(getErrorMessage(error));
  }
});

const statusSlice = createSlice({
  name: "status",
  initialState,
  reducers: {
    setRegistrationId: (state, action) => {
      state.registrationId = action.payload;
    },
    setErrors: (state, action) => {
      state.error = action.payload;
    },
    setLoading: (state, action) => {
      state.loading = action.payload;
    }

  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchStatusThunk.pending, (state) => {
        state.loading = true;
        // console.log("pending state");
        
        state.error = null;
      })
      .addCase(fetchStatusThunk.fulfilled, (state, action) => {
        state.loading = false;
        state.registrationId = action.payload.registrationId;
        state.name = action.payload.name;
        state.email = action.payload.email;
        state.eventName = action.payload.eventName;
        state.status = action.payload.status;
      })
      .addCase(fetchStatusThunk.rejected, (state, action) => {
        state.loading = false;
        state.error =
          action.payload ??
          action.error.message ??
          "Failed to fetch registration status";
      });
  },
});

export const { setErrors, setLoading, setRegistrationId } = statusSlice.actions;

export default statusSlice.reducer;
