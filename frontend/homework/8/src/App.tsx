import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/Navbar'
import './styles/scss/App.scss'
import { ErrorBoundary } from 'react-error-boundary'
import { lazy, Suspense } from 'react'
import { ProductProvider } from './context/ProductContext'
import { ErrorFallback } from './components/ErrorFallback'
import Loader from './components/loader/Loader'


const Home = lazy(() => import("./pages/Home").then(module => ({ default: module.Home }) ));
const ProductDetails = lazy(() => import("./pages/ProductDetails").then(module => ({ default: module.ProductDetails }) ));

function App() {
  return (
    <ProductProvider>
      <BrowserRouter>
        <div className="app">
          <Navbar />

          <main className="app-content">
            {/* <ErrorBoundary FallbackComponent={ErrorFallback}> */}
              <Suspense fallback={<Loader />}>
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/products/:id" element={<ProductDetails />} />
                </Routes>
              </Suspense>
            {/* </ErrorBoundary> */}
          </main>
        </div>
      </BrowserRouter>
    </ProductProvider>
  );
}


export default App
