
import { useNavigate } from "react-router-dom";
import { ProductCard } from "../components/ProductCard";
import Loader from "../components/loader/Loader";
import { useProductContext } from "../context/ProductContext";

export function Home() {
    const { 
        products, 
        loading, 
        error,
        searchQuery,
        searchResults,
    } = useProductContext();
    
    const navigate = useNavigate();

    const handleProductClick = (id: number) => {
        navigate(`/products/${id}`);
    };

    const displayProducts = searchQuery ? searchResults : products;
  
    if(loading) return <Loader />;
    if(error) return <p>{error.message}</p>;

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