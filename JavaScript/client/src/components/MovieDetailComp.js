import React, { useEffect, useState } from "react";

import axios from "axios";
const MovieDetail = (movieId) => {
  const [movie, setMovie] = useState();

  useEffect(() => {
    const getMovieDetails = async () => {
      let movieIdNum = movieId.movieId.movieId;
      try {
        const res = await axios.get(
          process.env.REACT_APP_MOVIES_API_MONGO + "/getId/" + movieIdNum,
          {
            withCredentials: true,
          }
        );
        setMovie(res.data);
        console.log(res.data)
      } catch (error) {
        console.log(error.response.data);
      }
    };

    getMovieDetails();
  }, []);


  return (
    <div>
      {movie && (
        <div>
          <div>{movie.title}</div>
          <div>{movie.director}</div>
          <div>{movie.date_added}</div>
          <div>{movie.rating}</div>
          <div>{movie.release_year}</div>
          <div>{movie.category}</div>
        </div>
      )}
    </div>
  );
};

export default MovieDetail;
