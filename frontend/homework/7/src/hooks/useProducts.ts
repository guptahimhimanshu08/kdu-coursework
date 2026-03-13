import { useEffect, useState } from "react";
import { getAllProducts } from "../api/products";
import type { Product } from "../types/Product";
import { STOCK_CONFIG } from "../constants/app.constants";

export function useProducts() {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      setError(null);

      try {
        const data = await getAllProducts();
        if (data.length === STOCK_CONFIG.EMPTY_ARRAY_LENGTH) {
          throw new Error("No products found");
        }
        setProducts(data);
      } catch (err) {
        if (err instanceof Error) {
          setError(err);
        } else {
          setError(new Error("Unknown error"));
        }
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  return { products, loading, error };
}