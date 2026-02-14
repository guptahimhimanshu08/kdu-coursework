import { NavLink } from "react-router-dom";

export default function Navbar() {
  return (
    <nav className="nav-bar">
        <h1> 🛍️Product Discovery</h1>
      <NavLink to="/">
        Home
      </NavLink>
    </nav>
  );
}