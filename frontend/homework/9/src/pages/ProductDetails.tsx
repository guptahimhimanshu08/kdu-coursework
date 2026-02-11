import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Loader from "../components/loader/Loader";
import { NAVIGATION, ARRAY_OFFSET } from "../constants/app.constants";
import { calculateDiscountedPrice } from "../utils/discount";
import { useAppDispatch, useAppSelector } from "../store";
import {
    fetchProductById,
    clearCurrentProduct
} from "../store/productSlice";
import { addToCart } from "../store/cartSlice";

export const ProductDetails = () => {
    const { id } = useParams();
    //  use dispatch and selector to get product details from the store
    const { product, loading, error } = useAppSelector((state) => state.products);
    const [addedToCart, setAddedToCart] = useState(false);

    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            dispatch(fetchProductById(Number(id)));
        }

        return () => {
            dispatch(clearCurrentProduct());
        };
    }, [id, dispatch]);


    const handleClick = (img: string) => {
        const mainImage = document.querySelector(".product-details__thumbnail") as HTMLImageElement;
        if (mainImage) {
            mainImage.src = img;
        }
    }
    if (loading) return <Loader />;
    if (error) return <p>{error}</p>;
    if (!product) return null;

    const discountedPrice = calculateDiscountedPrice(product.price, product?.discountPercentage)
    return (
        <div className="product-details">
            <div className="product-details__header">
                <button
                    type="button"
                    className="product-details__back-button"
                    onClick={() => navigate(NAVIGATION.HOME)}
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
                                <p className="product-details__price">${product.price}</p>
                                <p className="product-details__discount">
                                    ${discountedPrice}
                                </p>
                            </div>
                            <div>
                                <h3>Description</h3>
                                <p className="product-details__description">{product.description}</p>
                            </div>

                            <p className="product-details__rating">
                                Rating: {product.rating}
                            </p>

                            <button
                                type="button"
                                className="add-to-cart-button"
                                onClick={() => {
                                    dispatch(addToCart(product));
                                    setAddedToCart(true);
                                }}
                            >
                                {addedToCart ?
                                "Added to Cart" 
                                : "Add to Cart"}
                            </button>
                        </div>
                    </div>

                    <div className="product-details__gallery">
                        {product.images.map((img, index) => (
                            <img
                                role="button"
                                key={`${product.id}-${index}`}
                                src={img}
                                alt={`${product.title} ${index + ARRAY_OFFSET.DISPLAY_INDEX}`}
                                className="product-details__gallery-image"
                                onClick={() => handleClick(img)}
                                onKeyDown={(e) => {
                                    if (e.key === "Enter") {
                                        handleClick(img);
                                    }
                                }}
                            />
                        ))}
                    </div>
                </div>
            )}
        </div>
    )
}