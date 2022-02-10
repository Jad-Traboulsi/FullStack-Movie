import React, { useEffect, useState, useMemo } from "react";
import axios from "axios";

import { Link, useNavigate } from "react-router-dom";
import DatePicker from "react-datepicker";
import dayjs from "dayjs";
import countryList from "react-select-country-list";
import Select from "react-select";

import "react-datepicker/dist/react-datepicker.css";

const RegisterComponent = () => {
  const [state, setState] = useState({
    error: false,
    success: false,
    msg: "",
  });
  const [country, setCountry] = useState("");
  const options = useMemo(() => countryList().getData(), []);

  const [startDate, setStartDate] = useState(new Date());

  const [form, setForm] = useState({
    username: "",
    password: "",

    roleName: "Viewer",

    name: "",
    gender: "Male",
    email: "",
    birthDate: "",

    country: "",
    area: "",
    city: "",
    street: "",
    number: "",
  });
  const handlerOnChange = (event) => {
    const { value, name } = event.target;
    setForm({ ...form, [name]: value });
  };
  
  let navigate = useNavigate();

  const handlerOnSubmit = async (event) => {
    event.preventDefault();
    form.birthDate = dayjs(startDate).format("YYYY-MM-DD");
    form.country = country.label;
    console.log(form);
    window.scrollTo(0, 0);
    setState({ error: false, success: false, msg: "" });
    try {
      const res = await axios.post(
        process.env.REACT_APP_JAVA_API + "/users/addUser",
        form
      );

      console.log(res.data);
      setState({ error: false, success: true, msg: "Account Created! Redirecting to login page" });
      const timer = setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (error) {
      setState({ success: false, error: true, msg: error.response.data });
      console.log(error.response.data);
    }
  };

  // useEffect(() => {}, [form.email, form.password]);

  return (
    <div className="containerFormReg">
      {state.error && <div className="error">{state.msg}</div>}
      {state.success && <div className="success">{state.msg}</div>}
      <br />
      <form onSubmit={handlerOnSubmit} className="form-bordered">
        <div className="insideForm">
          <label className="form-label-title">Credentials</label>
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
        </div>

        <br />

        <div className="insideForm">
          <label className="form-label-title">Contact</label>
          <div className="form-group">
            <label htmlFor="name" className="form-label">
              Name
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="name"
              type="text"
              value={form.name}
              name="name"
              placeholder="Name"
              required={true}
            />
          </div>

          <div className="form-group">
            <label htmlFor="gender" className="form-label">
              Gender
            </label>
            <select
              onChange={handlerOnChange}
              name="gender"
              className="form-select"
            >
              <option defaultValue="Male">Male</option>
              <option value="Female">Female</option>
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="email" className="form-label">
              Email
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="email"
              type="text"
              value={form.email}
              name="email"
              placeholder="Email"
              required={true}
            />
          </div>

          <div className="form-group">
            <label htmlFor="birthDate" className="form-label">
              Date of Birth
            </label>
            <DatePicker
              className="form-input"
              selected={startDate}
              name="birthDate"
              dateFormat={"dd/MM/yyyy"}
              onChange={(date) => setStartDate(date)}
            />
          </div>
        </div>

        <br />
        <div className="insideForm">
          <label className="form-label-title">Address</label>
          <div className="form-group">
            <label htmlFor="country" className="form-label">
              Country
            </label>
            <Select
              options={options}
              value={country}
              onChange={(c) => setCountry(c)}
            />
          </div>
          <div className="form-group">
            <label htmlFor="area" className="form-label">
              Area
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="area"
              type="text"
              value={form.area}
              name="area"
              placeholder="Area"
              required={true}
            />
          </div>
          <div className="form-group">
            <label htmlFor="city" className="form-label">
              City
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="city"
              type="text"
              value={form.city}
              name="city"
              placeholder="City"
              required={true}
            />
          </div>
          <div className="form-group">
            <label htmlFor="street" className="form-label">
              Street
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="street"
              type="text"
              value={form.street}
              name="street"
              placeholder="Street"
              required={true}
            />
          </div>

          <div className="form-group">
            <label htmlFor="number" className="form-label">
              Number
            </label>
            <input
              className="form-input"
              onChange={handlerOnChange}
              id="number"
              type="number"
              value={form.number}
              min="1"
              name="number"
              placeholder="Number"
              required={true}
            />
          </div>
        </div>
        <br />
        <input
          type="submit"
          className="btn bg-purple-600 
            txt-white-100 
            hover:bg-purple-900 
            btn-rounded"
          value="Sign Up"
        />
        <div className="form-group txt-center txt-small">
          <Link to="/login">Already have an account ?</Link>
        </div>
      </form>
    </div>
  );
};

export default RegisterComponent;
