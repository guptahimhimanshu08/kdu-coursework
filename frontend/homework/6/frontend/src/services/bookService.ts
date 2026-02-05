import axios from "axios";
import type { Book } from "../types/Book";

const API_URL = 'http://localhost:3000/books';

export const fetchBooks = async (checkAvailability: boolean, genre: string, rating: string): Promise<Book[]>=>{

    try{
        const params: Record<string, any> = {};
        if (checkAvailability) {
            params.available = 'true';
        }
        if (genre) {
            params.genre = genre;
        }
        if (rating) {
            params.rating = rating;
        }
        
        const response  = await axios.get(API_URL, { params });
        return response.data;
    }
    catch(error){
        console.error('Error fetching books:', error);
        throw error;
    }
}

export const searchBooks = async (query: string, checkAvailability: boolean, genre: string, rating: string) : Promise<Book[]> => {
    try{
        const params: Record<string, any> = { q: query };
        if (checkAvailability) {
            params.available = 'true';
        }
        if (genre) {
            params.genre = genre;
        }
        if (rating) {
            params.rating = rating;
        }
        
        const response = await axios.get(`${API_URL}/search`, { params });
        return response.data;

    }catch(e){
        console.error('Error fetching searched books: ',e)
        throw e;
    }
}

export const fetchStats = async (): Promise<Record<string, any>> => {
    try{
        const response = await axios.get(`${API_URL}/stats`);   
        return response.data;
    }catch(e){  
        console.error('Error fetching stats: ', e);
        throw e;
    }
}