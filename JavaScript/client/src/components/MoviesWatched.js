import React, { useState, useEffect } from "react";
import axios from "axios";

import { useRecoilState } from "recoil";
import userState from "../atoms/userAtoms";

import { useNavigate } from "react-router-dom";

const MoviesWatched = () => {
  const [title, setTitle] = useState("");
  const [user, setUser] = useRecoilState(userState);
  const [movies, setMovies] = useState([]);
  const [foundMovies, setFoundMovies] = useState([]);

  let navigate = useNavigate();
  useEffect(() => {
    const getMoviesWatched = async () => {
      try {
        const res = await axios.get(
          process.env.REACT_APP_JAVA_API +
            "/seenMovies/getMoviesSeenBy/" +
            user.username,
          {
            withCredentials: true,
            headers: {
              Authorization: user.token,
            },
          }
        );
        setMovies(res.data);
        setFoundMovies(res.data)
      } catch (error) {
        console.log(error.response.data);
      }
    };
    getMoviesWatched();
  }, []);

  const filter = (e) => {
    const keyword = e.target.value;

    if (keyword !== "") {
      const results = movies.filter((movie) => {
        return movie.movie.title.toLowerCase().startsWith(keyword.toLowerCase());
      });
      setFoundMovies(results);
    } else {
      setFoundMovies(movies);
      // If the text field is empty, show all users
    }

    setTitle(keyword);
  };
  const handleId = (e) => {
    /*Well if the elements are nested event.target won't always work
     since it refers to the target that triggers the event in the first place.*/
    var movieId = e.target.id;
    navigate("/movie/" + movieId);
  };
  return (
    <div>
      <h1 className="title">Here is a list of what you watched!</h1>
      <br />
      <div className="flex">
        <input
          type="search"
          value={title}
          onChange={filter}
          className="flex-child search"
          placeholder="Filter"
        />
      </div>
      <br />
      {foundMovies &&
        foundMovies.length > 0 &&
        foundMovies.map((movie) => (
          <div
            key={movie.movie.external_id}
            id={movie.movie.external_id}
            className="card mb-10 relative"
            onClick={handleId}
          >
            <div className="card-body" id={movie.movie.external_id}>
              <p id={movie.movie.external_id}>{movie.movie.title}</p>
            </div>
          </div>
        ))}
    </div>
  );
};

export default MoviesWatched;
