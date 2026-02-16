import { BrowserRouter, Route, Routes } from "react-router-dom"
import BookingConfirmation from "./pages/Confirmation/BookingConfirmation"
import BookingPage from "./pages/Booking/BookingPage"
import { Navbar } from "./components/Navbar/Navbar"
import "./styles/App.scss"

function App() {


  return (
    <BrowserRouter>
    <Navbar />
      <Routes>
        <Route path="/" element={<BookingPage />} />
        <Route path="/booking/confirmation" element={<BookingConfirmation />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
