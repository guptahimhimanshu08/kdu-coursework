import { useAppDispatch, useAppSelector } from "../store";
import { removeFromCart, updateItemQuantity } from "../store/cartSlice";

export const CartDetails = () => {
    const dispatch = useAppDispatch();
    const { items } = useAppSelector((state) => state.cart);

    const totalPrice = items.reduce(
        (total, item) =>
            total + item.product.price * item.quantity,
        0
    );

    if (items.length === 0) {
        return <p>Cart is empty</p>;
    }

    return (
        <div>
            {items.map((item) => (
                <div key={item.product.id}>
                    <img src={item.product.thumbnail} width="50" />
                    <h3>{item.product.title}</h3>
                    <p>Price: ${item.product.price}</p>

                    <button
                        onClick={() =>
                            dispatch(
                                updateItemQuantity({
                                    id: item.product.id,
                                    quantity: item.quantity - 1
                                })
                            )
                        }
                        disabled={item.quantity === 1}
                    >
                        -
                    </button>

                    <span>{item.quantity}</span>

                    <button
                        onClick={() =>
                            dispatch(
                                updateItemQuantity({
                                    id: item.product.id,
                                    quantity: item.quantity + 1
                                })
                            )
                        }
                    >
                        +
                    </button>

                    <button
                        onClick={() =>
                            dispatch(removeFromCart({ id: item.product.id }))
                        }
                    >
                        Remove
                    </button>
                </div>
            ))}

            <h2>Total: ${totalPrice}</h2>
        </div>
    );
};