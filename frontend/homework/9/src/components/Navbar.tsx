import { NavLink } from "react-router-dom";
import { SearchBar } from "./SearchBar";
import { useAppSelector } from "../store";

export default function Navbar() {
    const cartCount = useAppSelector((state) =>
        state.cart.items.reduce(
            (total, item) => total + item.quantity,
            0
        )
    );
    return (
        <nav className="nav-bar">
            <h1> 🛍️Product Discovery</h1>
            <div className="nav-links">
                <NavLink to={"/"} className="home-link">
                    Home
                </NavLink>
                <SearchBar />
                <NavLink to={"/cart"} className="cart-link">
                    Cart <span className="cart-badge">{cartCount}</span>
                </NavLink>
            </div>
        </nav>
    );
}