import type { Book } from '../types/Book';
import '../styles/scss/App.css'

export const BookList = ({ books }: { books: Book[] }) => {


    return (
        <div className="book_list">
            
            <div className='book_list_items'>
                {
                    books.map((book) => (
                        <div key={book.id} className='book_list_item'>
                            <h3>{book.title}</h3>
                            <p><strong>Author:</strong> {book.author}</p>
                            <p><strong>Genre:</strong> {book.genre}</p>
                            <p><strong>Rating:</strong> {book.rating}</p>
                            <p>
                                <strong>Status:</strong>{' '}
                                {book.available ? (<span className='available'>Available</span>) : (<span className='unavailable'>Unavailable</span>)}
                            </p>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}