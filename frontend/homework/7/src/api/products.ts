import axios from "axios";
import type { Product, ProductsResponse } from "../types/Product";
import { API_DELAYS } from "../constants/app.constants";


const API_URL = `${import.meta.env.VITE_BASE_URL}`;

export const getAllProducts = async (): Promise<ProductsResponse> => {
    try{

        
        const response = await axios.get(API_URL);
    
        const res: ProductsResponse = response?.data;
        return res?.products || [];
    
    }catch(error){
        console.error("Error fetching products:", error);
        throw error;
    }
}

interface ID{
    id: number | undefined;
}
export const getProductById = async ({ id }: ID): Promise<Product> => {
    try{
        
        const response = await axios.get(`${API_URL}/${id}`);
        const data: Product = response?.data;
        return data;
    }catch(error){
        console.error(`Error fetching product with id ${id}:`, error);
        throw error;
    }
}