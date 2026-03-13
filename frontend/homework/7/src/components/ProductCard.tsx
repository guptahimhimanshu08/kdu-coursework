import { useMemo } from "react";
import type { Product } from "../types/Product";
import { PRICE_CONFIG, STOCK_CONFIG } from "../constants/app.constants";

interface ProductCardProps {
    product: Product;
    onClick: () => void;
}

export const ProductCard = ({ product, onClick }: ProductCardProps) => {

    const discountedPrice = useMemo(() => {
        if (product.discountPercentage > PRICE_CONFIG.MIN_DISCOUNT) {
            return (
                product.price -
                (product.price * product.discountPercentage) / PRICE_CONFIG.PERCENTAGE_DIVISOR
            ).toFixed(PRICE_CONFIG.DECIMAL_PLACES);
        }
        return product.price.toFixed(PRICE_CONFIG.DECIMAL_PLACES);
    }, [product.price, product.discountPercentage]);


    return (
        <div className="product-card" onClick={onClick}>
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

                <p className="stock">{STOCK_CONFIG.DEFAULT_STOCK_DISPLAY} in stock</p>
            </div>
        </div>
    )
}