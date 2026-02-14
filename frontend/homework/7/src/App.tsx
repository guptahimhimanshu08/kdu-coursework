import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/Navbar'
import { Home } from './pages/Home'
import { ProductDetails } from './pages/ProductDetails'
import './styles/scss/App.scss'

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <Navbar />

        <main className="app-content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/products/:id" element={<ProductDetails />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}


export default App
