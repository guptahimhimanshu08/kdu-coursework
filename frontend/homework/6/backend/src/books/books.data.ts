import { Genre } from 'src/common/enums/genre.enum';
import { Book } from './book.interface';

export const BOOKS: Book[] = [
  {
    id: 1,
    title: '1984',
    author: 'George Orwell',
    genre: Genre.FICTION,
    year: 1949,
    pages: 328,
    rating: 4.5,
    available: true,
    description: 'A dystopian novel about surveillance and totalitarianism.'
  },
  {
    id: 2,
    title: 'To Kill a Mockingbird',
    author: 'Harper Lee',
    genre: Genre.FICTION,
    year: 1960,
    pages: 281,
    rating: 4.8,
    available: false,
    description: 'A novel about racial injustice in the American South.'
  },
  {
    id: 3,
    title: 'The Pragmatic Programmer',
    author: 'Andrew Hunt',
    genre: Genre.NON_FICTION,
    year: 1999,
    pages: 352,
    rating: 4.7,
    available: true,
    description: 'A practical guide to software craftsmanship.'
  },
  {
    id: 4,
    title: 'Clean Code',
    author: 'Robert C. Martin',
    genre: Genre.NON_FICTION,
    year: 2008,
    pages: 464,
    rating: 4.6,
    available: true,
    description: 'Guidelines and best practices for writing clean code.'
  },
  {
    id: 5,
    title: 'Dune',
    author: 'Frank Herbert',
    genre: Genre.SCI_FI,
    year: 1965,
    pages: 412,
    rating: 4.4,
    available: false,
    description: 'An epic science fiction novel set on the desert planet Arrakis.'
  },
  {
    id: 6,
    title: 'The Hobbit',
    author: 'J.R.R. Tolkien',
    genre: Genre.FANTASY,
    year: 1937,
    pages: 310,
    rating: 4.9,
    available: true,
    description: 'A fantasy adventure preceding The Lord of the Rings.'
  },
  {
    id: 7,
    title: 'The Lord of the Rings',
    author: 'J.R.R. Tolkien',
    genre: Genre.FANTASY,
    year: 1954,
    pages: 1178,
    rating: 3.1,
    available: false
  }
];
