import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
  getAllProducts,
  getProductById,
  getSearchedProducts,
} from "../api/products";
import type { Product } from "../types/Product";

interface ProductState {
  products: Product[];
  product: Product | null;
  searchQuery: string;
  searchResults: Product[];
  loading: boolean;
  error: string | null;
}

const initialState: ProductState = {
  products: [],
  product: null,
  searchQuery: "",
  searchResults: [],
  loading: false,
  error: null,
};

const getErrorMessage = (error: unknown): string =>
  error instanceof Error ? error.message : "Something went wrong";

export const fetchProducts = createAsyncThunk<
  Product[],
  void,
  { rejectValue: string }
>("products/fetchAll", async (_, { rejectWithValue }) => {
  try {
    return await getAllProducts();
  } catch (error) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const fetchProductById = createAsyncThunk<
  Product,
  number,
  { rejectValue: string }
>("products/fetchById", async (id, { rejectWithValue }) => {
  try {
    return await getProductById({ id });
  } catch (error) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const fetchProductsBySearch = createAsyncThunk<
  Product[],
  string,
  { rejectValue: string }
>("products/search", async (query, { rejectWithValue }) => {
  try {
    const trimmed = query.trim();
    if (!trimmed) return [];

    return await getSearchedProducts({ query: trimmed });
  } catch (error) {
    return rejectWithValue(getErrorMessage(error));
  }
});

const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {
    setSearchQuery(state, action) {
      state.searchQuery = action.payload;
    },
    clearSearchResults(state) {
      state.searchQuery = "";
      state.searchResults = [];
    },
    clearCurrentProduct(state) {
      state.product = null;
    },

  },
  extraReducers: (builder) => {
    builder
      // fetch all products
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.products = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? action.error.message ?? "Failed to load products";
      })
      //   fetch By Id
      .addCase(fetchProductById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.loading = false;
        state.product = action.payload;
      })
      .addCase(fetchProductById.rejected, (state, action) => {
        state.loading = false;
        state.error =
          action.payload ?? action.error.message ?? "Failed to load product";
      })

      //   search query
      .addCase(fetchProductsBySearch.pending, (state, action) => {
        const query = action.meta.arg.trim();
        if (!query) return;

        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProductsBySearch.fulfilled, (state, action) => {
        state.loading = false;
        state.searchResults = action.payload;
      })
      .addCase(fetchProductsBySearch.rejected, (state, action) => {
        state.loading = false;

        const query = action.meta.arg.trim();
        if (!query) return;

        state.error = action.payload ?? action.error.message ?? "Search failed";
      });
  },
});

export const { setSearchQuery, clearSearchResults, clearCurrentProduct } =
  productSlice.actions;

export default productSlice.reducer;
