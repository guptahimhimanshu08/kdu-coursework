

export const BookCatalog = ({ stats }: { stats: Record<string, any> }) => {
    return (
        <div className="catalog_section">
            <h2>Book Catalog</h2>
            <div className='catalog_section_items'>
                <div className='catalog_section_item'>
                    <h3>Total Books</h3>
                    <h3>{stats.totalBooks}</h3>
                </div>
                <div className='catalog_section_item'>
                    <h3>Available Books</h3>
                    <h3>{stats.availableBooks}</h3>
                </div>
                <div className='catalog_section_item'>
                    <h3>Average Rating</h3>
                    <h3>{stats.averageRating}</h3>
                </div>
            </div>
        </div>
    )
}