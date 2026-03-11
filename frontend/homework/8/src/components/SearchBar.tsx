import { useProductContext } from "../context/ProductContext";

export const SearchBar = () => {
    const { searchQuery, setSearchQuery } = useProductContext();

    return(
        <div className="search-bar">
            <input
                type="text"
                placeholder="Search for Products..."
                value={searchQuery}
                onChange={(event) => setSearchQuery(event.target.value)}
            />
            <button className="cancel-search-btn" onClick={()=>setSearchQuery("")}>
                𐤕
            </button>
        </div>
    )
}