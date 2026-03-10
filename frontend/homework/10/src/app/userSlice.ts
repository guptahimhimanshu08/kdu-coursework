import { createSlice } from "@reduxjs/toolkit";

interface UserState {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  avatar: string;
  age: number;
}
const initialState: UserState = {
  firstName: "",
  lastName: "",
  email: "",
  phone: "",
  avatar: "",
  age: 0,
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setFirstName: (state, action) => {
      state.firstName = action.payload;
    },
    setLastName: (state, action) => {
      state.lastName = action.payload;
    },
    setEmail: (state, action) => {
      state.email = action.payload;
    },
    setPhone: (state, action) => {
      state.phone = action.payload;
    },
    setAge: (state, action) => {
      state.age = action.payload;
    }
  },
});

export const { setFirstName, setLastName, setEmail, setPhone, setAge } = userSlice.actions;
export default userSlice.reducer;
