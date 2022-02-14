import React, { useEffect, useState } from "react";
import axios from "axios";

import { useNavigate } from "react-router-dom";

const Movies = () => {
  const [movies, setMovies] = useState([]);
  
  let navigate = useNavigate();

  useEffect(() => {
    const getMovies = async () => {
      try {
        const res = await axios.get(
          process.env.REACT_APP_MOVIES_API_MONGO + "/getAll",
          {
            withCredentials: true,
          }
        );
        var rands = [];
        var randMovies = [];
        while (rands.length < 4) {
          var r = Math.floor(Math.random() * res.data.length);
          if (rands.indexOf(r) === -1) {
            rands.push(r);
            randMovies.push(res.data[r]);
          }
        }
        setMovies(randMovies);
      } catch (error) {
        console.log(error.response.data);
      }
    };

    getMovies();
  }, []);
const handleId = (e) => {
  /*Well if the elements are nested event.target won't always work
     since it refers to the target that triggers the event in the first place.*/
  var movieId = e.target.id;
  navigate("/movie/"+movieId)
};
  return (
    <div className="container pt-15">
      {movies.length > 0 && (
        <div className="row">
          <div
            id={movies[0].title}
            className="col col-quarter txt-center"
            onClick={handleId}
          >
            <div className="card relative" id={movies[0]._id}>
              <div className="card-body" id={movies[0]._id}>
                {movies[0].title}
              </div>
            </div>
          </div>
          <div className="col col-quarter txt-center" onClick={handleId}>
            <div className="card relative" id={movies[1]._id}>
              <div className="card-body" id={movies[1]._id}>
                {movies[1].title}
              </div>
            </div>
          </div>
          <div className="col col-quarter txt-center" onClick={handleId}>
            <div className="card relative" id={movies[2]._id}>
              <div className="card-body" id={movies[2]._id}>
                {movies[2].title}
              </div>
            </div>
          </div>
          <div className="col col-quarter txt-center" onClick={handleId}>
            <div className="card relative" id={movies[3]._id}>
              <div className="card-body" id={movies[3]._id}>
                {movies[3].title}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Movies;
