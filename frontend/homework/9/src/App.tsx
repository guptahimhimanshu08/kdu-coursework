import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/Navbar'
import './styles/scss/App.scss'
import { lazy, Suspense } from 'react'
import { Provider } from 'react-redux'
import { store } from './store'
import Loader from './components/loader/Loader'
import { CartDetails } from './pages/CartDetails'


const Home = lazy(() => import("./pages/Home").then(module => ({ default: module.Home }) ));
const ProductDetails = lazy(() => import("./pages/ProductDetails").then(module => ({ default: module.ProductDetails }) ));

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <div className="app">
          <Navbar />

          <main className="app-content">
            {/* <ErrorBoundary FallbackComponent={ErrorFallback}> */}
            <Suspense fallback={<Loader />}>
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/products/:id" element={<ProductDetails />} />
                <Route path="/cart" element={<CartDetails />} />
              </Routes>
            </Suspense>
            {/* </ErrorBoundary> */}
          </main>
        </div>
      </BrowserRouter>
    </Provider>
  );
}


export default App
