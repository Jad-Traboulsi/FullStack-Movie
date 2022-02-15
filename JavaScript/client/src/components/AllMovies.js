import React, { useEffect, useState } from "react";
import axios from "axios";

import { useNavigate } from "react-router-dom";

const AllMovies = () => {
  const [movies2D, setMovies2D] = useState([]);
  const [movies,setMovies]=useState([])
  const [foundMovies, setFoundMovies] = useState([]);

  const [title, setTitle] = useState("");
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
        const arr = [];
        const result = res.data
        while (result.length) arr.push(result.splice(0, 4));
        setMovies2D(arr);
        setFoundMovies(arr);
        var movies1D = [];
        for (var i = 0; i < arr.length; i++) {
          movies1D = movies1D.concat(arr[i]);
        }
        setMovies(movies1D)


      } catch (error) {
        console.log(error.response.data);
      }
    };

    getMovies();
  }, []);
  const handleId = (e) => {
    var movieId = e.target.id;
    navigate("/movie/" + movieId);
  };
   const filter = (e) => {
     const keyword = e.target.value;

     if (keyword !== "") {
       const results = movies.filter((movie) => {
         return movie.title.toLowerCase().startsWith(keyword.toLowerCase());
       });
       const results2D = []
        while (results.length) results2D.push(results.splice(0, 4));
       setFoundMovies(results2D);
     } else {
       setFoundMovies(movies2D);
       // If the text field is empty, show all users
     }

     setTitle(keyword);
   };

    // return (
    //   <div className="container pt-15">
        
    //     {movies2D.length > 0 &&
    //       movies2D.map((movieRow, index) => (
    //         <div className="row" key={index}>
    //           {movieRow.map((movie) => (
    //             <div
    //               key={movie._id}
    //               id={movie.title}
    //               className="col col-quarter txt-center"
    //               onClick={handleId}
    //             >
    //               <div className="card relative" id={movie._id}>
    //                 <div className="card-body" id={movie._id}>
    //                   {movie.title}
    //                 </div>
    //               </div>
    //             </div>
    //           ))}
    //         </div>
    //       ))}
    //   </div>
    // );
    return (
      <>
        <div className="container pt-15">
          <div className="flex">
            <input
              type="search"
              value={title}
              onChange={filter}
              className="flex-child search"
              placeholder="Filter"
            />
          </div>
          <br/>
          {foundMovies && foundMovies.length > 0 && (
            foundMovies.map((movieRow, index) => (
              <div className="row" key={index}>
                {movieRow.map((movie) => (
                  <div
                    key={movie._id}
                    id={movie.title}
                    className="col col-quarter txt-center"
                    onClick={handleId}
                  >
                    <div className="card relative" id={movie._id}>
                      <div className="card-body" id={movie._id}>
                        {movie.title}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ))
          )}
        </div>
      </>
    );

};

export default AllMovies;
