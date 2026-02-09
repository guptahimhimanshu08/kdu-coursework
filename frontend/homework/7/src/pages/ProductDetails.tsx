import { useNavigate, useParams } from "react-router-dom"
import Loader from "../components/loader/Loader";
import { useProduct } from "../hooks/useProduct";import { NAVIGATION, ARRAY_OFFSET } from "../constants/app.constants";

export const ProductDetails = () => {
    const { id } = useParams();
    const { product, loading, error } = useProduct({ id });
    const navigate = useNavigate();


    const handleClick = (img: string) => {
        const mainImage = document.querySelector(".product-details__thumbnail") as HTMLImageElement;
        if (mainImage) {
            mainImage.src = img;
        }
    }

    if (loading) return <Loader />;
    if (error) return <p>{error.message}</p>;
    if (!product) return null;
    return (
        <div className="product-details">
            <div className="product-details__header">
                <button
                    type="button"
                    className="product-details__back-button"
                    onClick={() => navigate(NAVIGATION.BACK_STEPS)}
                >
                    Back
                </button>
            </div>

            {product && (
                <div className="product-details__content">
                    <div className="product-details__main">
                        <div className="product-details__image">
                            <img
                                src={product.thumbnail}
                                alt={product.title}
                                className="product-details__thumbnail"
                            />
                        </div>

                        <div className="product-details__info">
                            <p className="product-details__brand">{product.brand}</p>
                            <h2 className="product-details__title">{product.title}</h2>
                            <div className="product-details__meta">

                                <p className="product-details__stock">
                                    📦{product.stock} in stock
                                </p>
                                <p className="product-details__category">
                                    🛒 {product.category}
                                </p>
                            </div>
                            <div className="product-details__pricing">
                                <p className="product-details__price">₹{product.price}</p>
                                <p className="product-details__discount">
                                    {product.discountPercentage}%
                                </p>
                            </div>
                            <div>
                                <h3>Description</h3>
                                <p className="product-details__description">{product.description}</p>
                            </div>



                            <p className="product-details__rating">
                                Rating: {product.rating}
                            </p>

                        </div>
                    </div>

                    <div className="product-details__gallery">
                        {product.images.map((img, index) => (
                            <img
                                key={index}
                                src={img}
                                alt={`${product.title} ${index + ARRAY_OFFSET.DISPLAY_INDEX}`}
                                className="product-details__gallery-image"
                                onClick={() => handleClick(img)}
                                // onKeyDown={(e) => {
                                    //     if (e.key === "Enter") {    
                                        //         handleClick(img);
                                        //     }
                                        // }}
                                        />
                        ))}
                    </div>
                </div>
            )}
        </div>
    )
}