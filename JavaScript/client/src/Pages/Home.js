import React,{useEffect} from 'react'
import Hello from '../components/Hello'
import { useRecoilState } from "recoil";

import axios from "axios";
import userState from "../atoms/userAtoms";
import Movies from '../components/RandMovies';
import Watched from './Watched';

const Home = () => {
    const [user, setUser] = useRecoilState(userState);

    return (
      <div>
        {user.isAuth && <Hello name={user.username} />}
        {!user.isAuth && <Hello name="" />}
        <Movies/>
      </div>
    );
}

export default Home
