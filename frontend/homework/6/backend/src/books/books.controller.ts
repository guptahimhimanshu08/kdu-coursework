import { Controller, Get, Query } from '@nestjs/common';
import { BooksService } from './books.service';
import { Book } from './book.interface';

interface QueryParams {
    available?: string;
    genre?: string;
    rating?: string;
}

@Controller('books')
export class BooksController {

    constructor(private readonly booksService: BooksService){}

    @Get()
    fetchBooks(@Query() params: QueryParams): Book[] {
        return this.booksService.fetchBooksFromData(params);
    }

    @Get('search')
    searchBooks(@Query('q') query: string, @Query() params: QueryParams): Book[] {
        return this.booksService.searchBooks(query, params);
    }

    @Get('available')
    getAvailableBooks(): Book[] {
        return this.booksService.getAvailableBooks();
    }

    @Get('year')
    getBooksByYearRange(@Query('startYear') startYear: string, @Query('endYear') endYear: string): Book[] {
        return this.booksService.getBooksByYearRange(Number(startYear), Number(endYear));
    }

    @Get('stats')
    getStats(): Record<string, any> {
        return this.booksService.getStats();
    }
}
