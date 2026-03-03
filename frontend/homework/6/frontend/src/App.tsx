
import './styles/scss/App.css'
import { useEffect, useState } from "react"
import { fetchBooks, fetchStats, searchBooks } from "./services/bookService";
import type { Book } from "./types/Book";
import { BookList } from "./components/BookList";
import { BookCatalog } from './components/BookCatalog';
import { Search } from './components/Search';
import { Genre } from './components/Genre';
import { Rating } from './components/Rating';

function App() {
  const [query, setQuery] = useState("");
  const [books, setBooks] = useState<Book[]>([]);
  const [checkAvailability, setCheckAvailability] = useState(false);
  const [genre, setGenre] = useState("");
  const [rating, setRating] = useState("");
  const [stats, setStats] = useState<Record<string, any>>({});

  useEffect(() => {
    const getBooks = async () => {
      try {

        let booksData;
        if (query.trim()) {
          booksData = await searchBooks(query, checkAvailability, genre, rating);
        }
        else booksData = await fetchBooks(checkAvailability, genre, rating);

        setBooks(booksData);
      } catch (e) {
        return e;
      }
    }

    const getStats = async () => {
      try {
        const fetchedStats = await fetchStats();
        setStats(fetchedStats);
      } catch (e) {
        return e;
      }
    }
    getBooks();
    getStats();
  }, [query, checkAvailability, genre, rating])

  return (
    <div className="app_container">
      <h1>Book Library</h1>

      <BookCatalog stats={stats} />

      <div className="query-items">

        <Search query={query} setQuery={setQuery} />

        <Genre genre={genre} setGenre={setGenre} />

        <Rating rating={rating} setRating={setRating} />

        <p>
          Show only Available?{''}
          <input type="checkbox" checked={checkAvailability} onChange={(e) => setCheckAvailability(e.target.checked)} />
        </p>
      </div>

      <BookList books={books} />
    </div>
  )
}

export default App
