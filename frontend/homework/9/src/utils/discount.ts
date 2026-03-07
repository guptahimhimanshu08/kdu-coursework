import { PRICE_CONFIG } from "../constants/app.constants";

export const calculateDiscountedPrice = (price: number, discountPercentage: number): string => {
    const discountedPrice = () => {
        if (discountPercentage > PRICE_CONFIG.MIN_DISCOUNT) {
            return (
                price -
                (price * discountPercentage) / PRICE_CONFIG.PERCENTAGE_DIVISOR
            ).toFixed(PRICE_CONFIG.DECIMAL_PLACES);
        }
        return price.toFixed(PRICE_CONFIG.DECIMAL_PLACES);
    };
    return discountedPrice();
}