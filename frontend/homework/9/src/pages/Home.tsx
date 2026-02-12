
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { ProductCard } from "../components/ProductCard";
import Loader from "../components/loader/Loader";
import { useAppDispatch, useAppSelector } from "../store";
import { fetchProducts } from "../store/productSlice";

export function Home() {
    const { 
        products, 
        loading, 
        error,
        searchQuery,
        searchResults,
    } = useAppSelector((state) => state.products);
    
    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        if (products.length === 0 && !loading) {
            dispatch(fetchProducts());
        }
    }, [dispatch, products.length, loading]);

    const handleProductClick = (id: number) => {
        navigate(`/products/${id}`);
    };

    const displayProducts = searchQuery ? searchResults : products;
  
    if(loading) return <Loader />;
    if(error) return <p>{error}</p>;

    return (
        <div className="products-grid">
            {displayProducts.length === 0 ? (
                <p>No products found{searchQuery ? ` for "${searchQuery}"` : ""}</p>
            ) : (
                displayProducts.map((product) => (
                    <ProductCard 
                        key={product.id}
                        product={product}
                        onClick={() => handleProductClick(product.id)}
                    />
                ))
            )}
        </div>
    )
}