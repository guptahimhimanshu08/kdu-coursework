import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { BookingPayload } from "../types/BookingPayload";

export type BookingDraftState = BookingPayload;

export const initialState: BookingDraftState = {
  acceptedTerms: false,
  selection: {
    cleaningTypeId: "",
    frequencyId: "",
    bedrooms: 1,
    bathrooms: 1,
    hours: 2,
    date: "",
    startTime: "",
    extras: [] as string[],
    specialInstructions: "",
  },
  customer: {
    email: "",
    phone: "",
    address: "",
    zip: "",
  },
  payment: {
    cardNumber: "",
    expiry: "",
    cvv: "",
    nameOnCard: "",
  },
};

const bookingDraftSlice = createSlice({
  name: "bookingDraft",
  initialState,
  reducers: {
    resetDraft: () => initialState,

    setAcceptedTerms: (state, action: PayloadAction<boolean>) => {
      state.acceptedTerms = action.payload;
    },
    setCleaningTypeId: (state, action: PayloadAction<string>) => {
      state.selection.cleaningTypeId = action.payload;
    },
    setFrequencyId: (state, action: PayloadAction<string>) => {
      state.selection.frequencyId = action.payload;
    },
    setBedrooms: (state, action: PayloadAction<number>) => {
      state.selection.bedrooms = action.payload;
    },
    setBathrooms: (state, action: PayloadAction<number>) => {
      state.selection.bathrooms = action.payload;
    },
    setHours: (state, action: PayloadAction<number>) => {
      state.selection.hours = action.payload;
    },
    setDate: (state, action: PayloadAction<string>) => {
      state.selection.date = action.payload;
    },
    setStartTime: (state, action: PayloadAction<string>) => {
      state.selection.startTime = action.payload;
    },

    setExtras: (state, action: PayloadAction<string>) => {
      const id = action.payload;
      if (!Array.isArray(state.selection.extras)) {
        state.selection.extras = [];
      }

      const i = state.selection.extras.indexOf(id);

      if (i >= 0) {
        state.selection.extras.splice(i, 1);
      } else {
        state.selection.extras.push(id); 
      }
    },
    clearExtras: (state) => {
      state.selection.extras = [];
    },
    setSpecialInstructions: (state, action: PayloadAction<string>) => {
      state.selection.specialInstructions = action.payload;
    },
    setCustomerEmail: (state, action: PayloadAction<string>) => {
      state.customer.email = action.payload;
    },
    setCustomerPhone: (state, action: PayloadAction<string>) => {
      state.customer.phone = action.payload;
    },
    setCustomerAddress: (state, action: PayloadAction<string>) => {
      state.customer.address = action.payload;
    },
    setCustomerZip: (state, action: PayloadAction<string>) => {
      state.customer.zip = action.payload;
    },

    setCardNumber: (state, action: PayloadAction<string>) => {
      state.payment.cardNumber = action.payload;
    },
    setCardExpiry: (state, action: PayloadAction<string>) => {
      state.payment.expiry = action.payload;
    },
    setCardCVV: (state, action: PayloadAction<string>) => {
      state.payment.cvv = action.payload;
    },
    setNameOnCard: (state, action: PayloadAction<string>) => {
      state.payment.nameOnCard = action.payload;
    },
    setPaymentDetails: (
      state,
      action: PayloadAction<{ key: string; value: any }>,
    ) => {
      const { key, value } = action.payload;
      (state.payment as any)[key] = value;
    },
    setPersonalDetails: (
      state,
      action: PayloadAction<{ key: string; value: any }>,
    ) => {
      const { key, value } = action.payload;
      (state.customer as any)[key] = value;
    },
  },
});

export const {
  resetDraft,
  setAcceptedTerms,
  setCleaningTypeId,
  setFrequencyId,
  setBedrooms,
  setBathrooms,
  setHours,
  setDate,
  setStartTime,
  setExtras,
  clearExtras,
  setCustomerEmail,
  setCustomerPhone,
  setCustomerAddress,
  setCustomerZip,
  setPaymentDetails,
  setPersonalDetails,
} = bookingDraftSlice.actions;
export default bookingDraftSlice.reducer;
