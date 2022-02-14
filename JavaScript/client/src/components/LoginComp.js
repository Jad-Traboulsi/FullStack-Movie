import React, { useEffect, useState } from "react";
import axios from "axios";
import { useRecoilState } from "recoil";

import userState from "../atoms/userAtoms";

import { Link, useNavigate } from "react-router-dom";

const LoginComponent = () => {
  const [state, setState] = useState({
    error: false,
    msg: "",
  });
  let navigate = useNavigate();
  const [form, setForm] = useState({
    username: "",
    password: "",
  });

  const [user, setUser] = useRecoilState(userState);

  const handlerOnChange = (event) => {
    const { value, name } = event.target;
    setForm({ ...form, [name]: value });
  };

  const handlerOnSubmit = async (event) => {
    event.preventDefault();

    console.log(form);
    try {
      
      setState({ error: false, msg: "OK" });
      const res = await axios.post(
        process.env.REACT_APP_JAVA_API + "/users/authenticate",
        form,
        {
          withCredentials: true,
        }
      );

      setUser({
        isAuth: true,
        username: res.data.username,
        email:res.data.email,
        token: "Bearer "+res.data.jwtToken,
      });
      console.log(res.data);

      navigate("/");
    } catch (error) {
      setState({ error: true, msg: "Invalid Username/Password" });
      console.log(error.response.data);
    }
  };

  useEffect(() => {}, []);

  return (
    <div>
      <div className="containerForm">
        {state.error && <div className="error">{state.msg}</div>}
        <form onSubmit={handlerOnSubmit} className="form-bordered">
          <div className="form-group">
            <label htmlFor="username" className="form-label">
              Username
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="username"
              type="text"
              value={form.username}
              name="username"
              placeholder="Username"
              required={true}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password" className="form-bordered">
              Password
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="password"
              type="password"
              value={form.password}
              name="password"
              placeholder="Password"
              required={true}
            />
          </div>
          <div className="flex">
            <input
              type="submit"
              className="flex-child btn bg-purple-600 
              txt-white-100 
              hover:bg-purple-900 
              btn-rounded"
              value="Log In"
            />
          </div>
          <div className="form-group txt-center txt-small">
            <Link to="/register">Don't have any account ?</Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginComponent;
