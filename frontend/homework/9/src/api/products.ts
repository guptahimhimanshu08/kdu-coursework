import axios from "axios";
import type { Product, ProductsResponse } from "../types/Product";


const API_URL = `${import.meta.env.VITE_BASE_URL}`;

export const getAllProducts = async (): Promise<Product[]> => {
    try{        
        const response = await axios.get(API_URL);
    
        return response?.data?.products || [];
    
    }catch(error){
        if(error instanceof Error){
            throw error;
        }
        throw new Error("Unknown error");
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
        if(error instanceof Error){
            throw error;
        }
        throw new Error("Unknown error");
    }
}

interface Query{
    query: string | undefined;
}
export const getSearchedProducts = async ({query}: Query): Promise<Product[]> => {
    try{
        const response = await axios.get(`${API_URL}/search?q=${query}`);
        const data: ProductsResponse = response?.data;
        return data.products || [];

    }catch(error){
        if(error instanceof Error){
            throw error;
        }
        throw new Error("Unknown error");
    }
}