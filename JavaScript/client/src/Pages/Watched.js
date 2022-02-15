import React from "react";
import MoviesWatched from "../components/MoviesWatched";
import { useRecoilState } from "recoil";
import { Link } from "react-router-dom";

import userState from "../atoms/userAtoms";
const Watched = () => {
  const [user, setUser] = useRecoilState(userState);

  return (
    <>
      {user.isAuth ? (
        <div>
          <MoviesWatched />
        </div>
      ) : (
        <div className="error">
          <span>
            You arent logged in
            <br />
            <br />
            <br />
            <Link to="/login">Log In</Link>
          </span>
        </div>
      )}
    </>
  );
};

export default Watched;
