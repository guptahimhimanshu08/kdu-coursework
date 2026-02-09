
import { useNavigate } from "react-router-dom";
import { ProductCard } from "../components/ProductCard";
import Loader from "../components/loader/Loader";
import { useProducts } from "../hooks/useProducts";

export function Home() {
    const { products, loading, error } = useProducts();
 
    const navigate = useNavigate();

    const handleProductClick = (id: number) => {
        navigate(`/products/${id}`);
    };

    if(loading) return <Loader />;
    if(error) return <p>{error.message}</p>;

    return (
        <div className="products-grid">

            {products.map((product) => (
                <ProductCard key={product.id}
                product={product}
                onClick = {()=> handleProductClick(product.id)}
                />
            ))}
        
        </div>
    )
}