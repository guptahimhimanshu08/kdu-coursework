import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import type { RegistrationRequest } from "../types/RegistrationRequest";
import { postRegistration } from "../services/registrationService";
import type { RegisterResponse } from "../types/RegisterResponse";

interface Form {
  name: string;
  email: string;
  eventName: string;
  message?: string;
}

interface RegistrationState {
  form: Form;
  loading: boolean;
  error: string | null;
}

const initialState: RegistrationState = {
  form: {
    name: "",
    email: "",
    eventName: "",
    message: "",
  },
  loading: false,
  error: null,
};

const getErrorMessage = (error: unknown): string =>
  error instanceof Error ? error.message : "Something went wrong";

export const registerThunk = createAsyncThunk<
  string,
  RegistrationRequest,
  { rejectValue: string }
>("registration/register", async (registrationData, { rejectWithValue }) => {
    try {
      // console.log("Inside thunk");
      
        const response = await postRegistration(registrationData);
        // console.log(response.registrationId);
        // console.log(typeof(response.registrationId));
        
        return response.registrationId;
    } catch (error) {
        return rejectWithValue(getErrorMessage(error));
    }
});

const registerSlice = createSlice({
  name: "registration",
  initialState,
  reducers: {
    setErrors: (state, action) => {
      state.error = action.payload;
    },
    setForm: (state, action) => {
      state.form = { ...state.form, ...action.payload };
    },
    clearForm: (state) => {
      state.form = initialState.form;
    },
    setLoading: (state, action) => {
      state.loading = action.payload;
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerThunk.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(registerThunk.fulfilled, (state, action) => {
        state.loading = false;
        state.form = initialState.form;
      })
      .addCase(registerThunk.rejected, (state, action) => {
        state.loading = false;
        state.error =
          action.payload ?? action.error.message ?? "Failed to register for event";
      })
      
  },
});

export const FormDetails = (state: {form: Form}) => state.form;
export const { setErrors, setForm, clearForm, setLoading } =
  registerSlice.actions;

export default registerSlice.reducer;
