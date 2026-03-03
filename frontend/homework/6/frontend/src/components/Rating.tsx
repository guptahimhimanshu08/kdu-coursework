export const Rating = ({ rating, setRating }: { rating: string; setRating: (rating: string) => void })=>{
    return(
        <select className="rating-select" value={rating} onChange={(e) => setRating(e.target.value)}>
          <option value="">All Ratings</option>
          <option value="3.0">3.0+</option>
          <option value="4.0">4.0+</option>
          <option value="4.5">4.5+</option>
        </select>
    )
}