import { createSlice } from "@reduxjs/toolkit";
import type { Product } from "../types/Product";

interface CartItem{
    product: Product;
    quantity: number;
}
interface CartState{
    items: CartItem[];
    // price: number;
}
const initialState: CartState = {
    items: [],
    // price: 0,
}

const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers:{
        addToCart: (state, action) => {
            const item = state.items.find(
                (i) => i.product.id === action.payload.id
            )

            if(item){
                item.quantity += 1;
            }else{
                state.items.push({ 
                    product: action.payload, 
                    quantity: 1 
                });
            }
        },
        removeFromCart: (state, action) => {
            state.items = state.items.filter(
                (i) => i.product.id !== action.payload.id
            )
        },
        clearCart: (state) => {
            state.items = [];
            // state.price = 0;
        },
        updateItemQuantity: (state, action) => {
                const item = state.items.find(
                    (i) => i.product.id === action.payload.id
                )
                if(item){
                    item.quantity = action.payload.quantity;
                }

        }
    }
})

export const {addToCart, removeFromCart, clearCart, updateItemQuantity} = cartSlice.actions;
export default cartSlice.reducer;