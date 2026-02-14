import {  NavLink } from "react-router-dom";
import { SearchBar } from "./SearchBar";

export default function Navbar() {
    return (
        <nav className="nav-bar">
            <h1> 🛍️Product Discovery</h1>
            <div className="nav-items-2">
                <NavLink to={"/"}>
                    Home
                </NavLink>
                <SearchBar />
            </div>
        </nav>
    );
}