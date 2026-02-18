import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import ErrorBoundary from './components/ErrorBoundary/ErrorBoundary';
import { Provider } from 'react-redux';
import { store } from './app/store';
import { RegistrationPage } from './pages/RegistrationPage/RegistrationPage';
import { BookingConfirmation } from './pages/BookingConfirmation/BookingConfirmation';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <RegistrationPage />,
      },
      {
        path: "/confirmation",
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
