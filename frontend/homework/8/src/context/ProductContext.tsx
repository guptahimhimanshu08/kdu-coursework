import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from "react";
import { getAllProducts, getProductById, getSearchedProducts } from "../api/products";
import type { Product } from "../types/Product";
import { STOCK_CONFIG } from "../constants/app.constants";

interface ProductContextType {
    products: Product[];
    product: Product | null;
    loading: boolean;
    error: Error | null;
    setCurrentProductId: (id: number | null) => void;
    refetchProducts: () => Promise<void>;
    searchQuery: string;
    setSearchQuery: (query: string) => void;
    searchResults: Product[];

}

const ProductContext = createContext<ProductContextType | undefined>(undefined);

interface ProductProviderProps {
    children: ReactNode;
}


export function ProductProvider({ children }: Readonly<ProductProviderProps>) {

    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [product, setProduct] = useState<Product | null>(null);
    const [currentProductId, setCurrentProductId] = useState<number | null>(null);
    const [error, setError] = useState<Error | null>(null);
    const [searchQuery, setSearchQuery] = useState<string>("");
    const [searchResults, setSearchResults] = useState<Product[]>([]);

    const fetchProducts = async () => {
        setError(null);
        setLoading(true);

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

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProductById = async () => {
        setLoading(true);
        setError(null);

        try {
            const data = await getProductById({ id: Number(currentProductId) });
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

    useEffect(() => {
        if (currentProductId === null) return;
        fetchProductById();
    }, [currentProductId]);

    const getProductsOnSearch= async () => {
        setLoading(true);
        setError(null);

        try {
            const data = await getSearchedProducts({ query: searchQuery });
            setSearchResults(data);
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

    useEffect(()=>{ 
       if (!searchQuery || searchQuery.trim() === "") {
            setSearchResults([]);
            return;
        }

        const timer = setTimeout(()=>{
            getProductsOnSearch();
        }, 500)
        return () => clearTimeout(timer);
    },[searchQuery])

    const value: ProductContextType = useMemo(() => ({
        products,
        loading,
        error,
        product,
        setCurrentProductId,
        refetchProducts: fetchProducts,
        searchQuery,
        setSearchQuery,
        searchResults,
    }), [products, loading, error, product, setCurrentProductId, fetchProducts, searchQuery, setSearchQuery, searchResults]);

    return (
        <ProductContext.Provider value={value}>
            {children}
        </ProductContext.Provider>
    );
}

export function useProductContext() {
    const context = useContext(ProductContext);

    if (context === undefined) {
        throw new Error("useProductContext must be used within a ProductProvider");
    }

    return context;
}
