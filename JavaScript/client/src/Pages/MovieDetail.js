import React from 'react'
import { useParams } from 'react-router-dom';
import MovieDetailComp from '../components/MovieDetailComp'

const MovieDetail = () => {
    let movieId = useParams() 
  return (
    <div>
      <MovieDetailComp movieId={movieId} />
    </div>
  );
}

export default MovieDetail