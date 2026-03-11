import type { Product } from "../types/Product";
import { PRICE_CONFIG } from "../constants/app.constants";
import { calculateDiscountedPrice } from "../utils/discount";

interface ProductCardProps {
    product: Product;
    onClick: () => void;
}

export const ProductCard = ({ product, onClick }: ProductCardProps) => {

    const discountedPrice = calculateDiscountedPrice(product.price, product.discountPercentage);

    return (
        <div role="button" className="product-card" onClick={onClick}>
            <div className="image-wrapper">
                <span className ="discount-badge">{`${product.discountPercentage}%`}</span>
                <span className="rating-badge">{`⭐ ${product.rating}`}</span>
                <img src={product.thumbnail} alt="product" />
            </div>

            <div className="content">
                <p className="brand">{product.brand}</p>
                <h3 className="title">{product.title}</h3>

                <div className="price">
                    <span className="original">${product.price.toFixed(PRICE_CONFIG.DECIMAL_PLACES)}</span>
                    <span className="final">${discountedPrice}</span>
                </div>

                <p className="stock">{product.stock} in stock</p>
            </div>
        </div>
    )
}