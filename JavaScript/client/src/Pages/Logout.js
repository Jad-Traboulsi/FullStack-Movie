import { useEffect } from "react";
import axios from "axios";
import { useRecoilState } from "recoil";
import { useNavigate } from "react-router-dom";

import userState from "../atoms/userAtoms";

const Logout = () => {
  let navigate = useNavigate();
  const [user, setUser] = useRecoilState(userState);

  useEffect(() => {
    const logout = async () => {
      if (user.isAuth) {
        try {
          setUser({
            isAuth: false,
            username: "",
            email: "",
            token: "",
          });
        } catch (error) {
          console.log(error.response.data);
        }
      }
      navigate("/");
    };

    logout();
  }, []);

  return null;
};

export default Logout;
