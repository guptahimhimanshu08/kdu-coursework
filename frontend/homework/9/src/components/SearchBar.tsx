import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../store";
import {
  setSearchQuery,
  clearSearchResults,
  fetchProductsBySearch,
  fetchProducts,
} from "../store/productSlice";
export const SearchBar = () => {
    //  fetch states from redux store
    const { searchQuery} = useAppSelector((state) => state.products);
    const dispatch = useAppDispatch();

    useEffect(()=>{

        const trimmedQuery = searchQuery.trim();

        const timeout = setTimeout(()=>{
            if(trimmedQuery) {
                dispatch(fetchProductsBySearch(trimmedQuery));
            } else {
                dispatch(clearSearchResults());
            }
        }, 500);
        return () => clearTimeout(timeout);
    }, [searchQuery, dispatch]);
    
    return(
        <div className="search-bar">
            <input
                type="text"
                placeholder="Search for Products..."
                value={searchQuery}
                onChange={(event) => dispatch(setSearchQuery(event.target.value))}
            />
            <button className="cancel-search-btn" onClick={() => {
                dispatch(setSearchQuery(""));
                dispatch(clearSearchResults());
            }}>
                𐤕
            </button>
        </div>
    )
}