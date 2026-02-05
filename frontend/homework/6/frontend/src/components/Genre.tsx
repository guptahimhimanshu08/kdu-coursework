
export const Genre = ({ genre, setGenre }: { genre: string; setGenre: (genre: string) => void })=>{
    return(
        <select className="genre-select" value={genre} onChange={(e) => setGenre(e.target.value)}>
          <option value="">All Genres</option>
          <option value="Fiction">Fiction</option>
          <option value="Non-Fiction">Non-Fiction</option>
          <option value="Sci-Fi">Sci-Fi</option>
          <option value="Fantasy">Fantasy</option>
        </select>
    )
}