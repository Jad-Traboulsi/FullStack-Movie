import React, { useContext} from "react";
import { ThemeContext } from "./contexts/themes";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./Pages/Home";
import LogIn from "./Pages/LogIn";
import Logout from "./Pages/Logout";
import Register from "./Pages/Register";
import Account from "./Pages/Account";
import Default from "./layouts/Default";
import MovieDetail from "./Pages/MovieDetail";
import Watched from "./Pages/Watched";
import Movies from "./Pages/Movies";
const App = () => {
  const [{ theme }] = useContext(ThemeContext);

  return (
    <BrowserRouter>
      <div className="app" style={theme}>
        <Routes>
          <Route
            path="/"
            element={
              <Default>
                <Home />
              </Default>
            }
          />
          <Route
            path="/login"
            element={
              <Default>
                <LogIn />
              </Default>
            }
          />
          <Route
            path="/register"
            element={
              <Default>
                <Register />
              </Default>
            }
          />
          <Route
            path="/logout"
            element={
              <Default>
                <Logout />
              </Default>
            }
          />
          <Route
            path="/account"
            element={
              <Default>
                <Account />
              </Default>
            }
          />
          <Route
            path="/movie/:movieId"
            element={
              <Default>
                <MovieDetail />
              </Default>
            }
          />
          <Route
            path="/moviesWatched"
            element={
              <Default>
                <Watched />
              </Default>
            }
          />
          <Route
            path="/movies"
            element={
              <Default>
                <Movies />
              </Default>
            }
          />
        </Routes>
      </div>
    </BrowserRouter>
  );
};

export default App;
