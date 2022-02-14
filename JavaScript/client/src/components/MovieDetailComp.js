import React, { useEffect, useState } from "react";
import axios from "axios";

import { useRecoilState } from "recoil";
import userState from "../atoms/userAtoms";
import dayjs from "dayjs";

const MovieDetail = (movieId) => {
  const [movie, setMovie] = useState();
  const [watched, setWatched] = useState(false);
  const [watchedOn, setWatchedOn] = useState("");
  const [user, setUser] = useRecoilState(userState);

  const getIfWatched = async () => {
    let movieIdNum = movieId.movieId.movieId;
    if (user.isAuth) {
      try {
        const res = await axios.get(
          process.env.REACT_APP_JAVA_API +
            "/seenMovies/getIfMovieSeen/" +
            user.username +
            "/" +
            movieIdNum,
          {
            withCredentials: true,
            headers: {
              Authorization: user.token,
            },
          }
        );
        setWatched(res.data);
        if (res.data) {
          try {
            const res2 = await axios.get(
              process.env.REACT_APP_JAVA_API +
                "/seenMovies/getWhenMovieSeen/" +
                user.username +
                "/" +
                movieIdNum,
              {
                withCredentials: true,
                headers: {
                  Authorization: user.token,
                },
              }
            );
            setWatchedOn(res2.data);
          } catch (error) {
            console.log(error.response.data);
          }
        }
      } catch (error) {
        console.log(error.response.data);
      }
    }
  };

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
      } catch (error) {
        console.log(error.response.data);
      }
    };

    getMovieDetails();

    getIfWatched();
  }, []);

  const watchNow = async () => {
    console.log(user.username);
    console.log(movie._id);
    console.log(dayjs());
    try {
      const res = await axios.post(
        process.env.REACT_APP_JAVA_API + "/seenMovies/addMovieToUser",
        {
          username: user.username,
          externalId: movie._id,
          date: dayjs().format("YYYY-MM-DD"),
        },
        {
          withCredentials: true,
          headers: {
            Authorization: user.token,
          },
        }
      );
      console.log(res.data);
      getIfWatched();
    } catch (error) {
      console.log(error.response.data);
    }
  };
  return (
    <div>
      {movie && (
        <div>
          <div className="movieTitle">{movie.title}</div>
          <div>{movie.director}</div>

          <br />
          {user.isAuth && watched && (
            <div className="metadataWrapper">
              <span className="metadataItem">
                Watched on: {dayjs(watchedOn).format("DD-MMM-YYYY")}{" "}
              </span>
            </div>
          )}

          {user.isAuth && !watched && (
            <input
              type="button"
              className="btn bg-purple-600 
            txt-white-100 
            hover:bg-purple-900 
            btn-rounded"
              value="Watch Now"
              onClick={watchNow}
            />
          )}

          <br />
          <div className="metadataWrapper">
            <span className="metadataItem">{movie.release_year}</span>
            <span className="metdataSeparator"> | </span>
            <span className="rating">
              <span className="metadataItem">{movie.rating}</span>
            </span>
            <span className="metdataSeparator"> | </span>
            <span className="metadataItem">{movie.category}</span>
          </div>
          <br />
          <div className="metadataItem">Added on: {movie.date_added}</div>
        </div>
      )}
    </div>
  );
};

export default MovieDetail;
