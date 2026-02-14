import { useEffect, useState } from "react";
import { getProductById } from "../api/products";
import type { Product } from "../types/Product";

interface ID{
    id: string | undefined;
}

export function useProduct({ id }: ID) {
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    if (!id) return;

    const fetchProduct = async () => {
      setLoading(true);
      setError(null);

      try {
        const data = await getProductById({ id: Number(id) });
        setProduct(data);
      } catch (err) {
        if (err instanceof Error) {
          setError(err);
        } else {
          setError(new Error("An unknown error occurred"));
        }
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  return { product, loading, error };
}
