
export const Search = ({ query, setQuery }: { query: string; setQuery: (query: string) => void })=>{
    return (
        <input className="search-input" value={query} onChange={(e) => setQuery(e.target.value)}
          placeholder="Search by title or author" />
    )
}