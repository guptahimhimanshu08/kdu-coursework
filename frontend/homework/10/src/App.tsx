import { lazy, Suspense } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Loader from "./components/customLoader/Loader";
import { Provider } from "react-redux";
import { store } from "./app/store";


const Home = lazy(() => import("./pages/Home").then(module => ({ default: module.Home })));
const UserDetails = lazy(() => import("./pages/UserDetails").then(module => ({ default: module.UserDetails})));

function App() {

  return (
    <Provider store={store}>
      <BrowserRouter>
        <div className="app">

          <main className="app-content">
            {/* <ErrorBoundary FallbackComponent={ErrorFallback}> */}
            <Suspense fallback={<Loader />}>
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/users/:id" element={<UserDetails />} />
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
