import { Injectable } from '@nestjs/common';
import { BOOKS } from './books.data';
import { Book } from './book.interface';

@Injectable()
export class BooksService {
    
    private readonly books = BOOKS;

    fetchBooksFromData(params: Record<string, any>): Book[]{
        let books  = this.books;
        if(Object.keys(params).length === 0){
            return this.books;
        }
        
        if(params['available'] !== undefined && params['available'] !== ''){
            
            books = books.filter(book => book.available === (params['available'] === 'true'));
        }
        if(params['genre'] !== undefined && params['genre'] !== ''){
            books = books.filter(book => book.genre === params['genre']);
        }
        if(params['rating'] !== undefined && params['rating'] !== ''){
            const ratingValue = Number.parseFloat(params['rating']);
            books = books.filter(book => book.rating >= ratingValue);
        }
        return books;
    }

    searchBooks(query: string, params: Record<string, any>): Book[]{
        query = query.toLowerCase();
        let books = this.fetchBooksFromData(params);
        
        books = books.filter(book => 
            book.title.toLowerCase().includes(query) ||
            book.author.toLowerCase().includes(query)
        );
        
        console.log(books);
        return books;

    }
    getAvailableBooks(): Book[] {
        return this.books.filter(book => book.available);
    }

    getBooksByYearRange(startYear: number, endYear: number): Book[]{
        return this.books.filter(book => book.year >= startYear && book.year <= endYear);
    }

    getStats(): Record<string, any> {
        const totalBooks = this.books.length;
        const availableBooks = this.books.filter(book => book.available).length;
        const averageRating = this.books.reduce((sum, book) => sum + book.rating, 0) / totalBooks;
        return {
            totalBooks,
            availableBooks,
            averageRating: Number.parseFloat(averageRating.toFixed(2)),
        };
    }
}
