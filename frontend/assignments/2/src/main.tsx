import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import App from './App.tsx'
import { Provider } from 'react-redux'
import { store } from './app/store.ts'
import BookingPage from './pages/Booking/BookingPage'
import BookingConfirmation from './pages/Confirmation/BookingConfirmation'
import ErrorBoundary from './components/ErrorBoundary/ErrorBoundary'
import './styles/App.scss'

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <BookingPage />,
      },
      {
        path: "/booking/confirmation",
        element: <BookingConfirmation />,
      },
    ],
  },
]);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <ErrorBoundary>
      <Provider store={store}>
        <RouterProvider router={router} />
      </Provider>
    </ErrorBoundary>
  </StrictMode>,
)
