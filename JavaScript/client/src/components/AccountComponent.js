import React, { useEffect, useState, useMemo } from "react";
import axios from "axios";
import { useRecoilState } from "recoil";

import userState from "../atoms/userAtoms";

import { Link, useNavigate } from "react-router-dom";
import DatePicker from "react-datepicker";
import dayjs from "dayjs";
import countryList from "react-select-country-list";
import Select from "react-select";

import "react-datepicker/dist/react-datepicker.css";
const AccountComponent = () => {


  const [user, setUser] = useRecoilState(userState);
  const [state, setState] = useState({
    error: false,
    success: false,
    msg: "",
  });
  const [country, setCountry] = useState("");
  const [editState, setEditState] = useState(false);
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
    form.oldEmail = user.email;
    form.oldUsername = user.username;
    console.log(form);
    console.log(user)
    window.scrollTo(0, 0);
    setState({ error: false, success: false, msg: "" });
    try {
      const res = await axios.patch(
        process.env.REACT_APP_JAVA_API + "/users/updateUser",
        form,
        {
          withCredentials: true,
          headers: {
            Authorization: user.token,
          },
        }
      );

      console.log(res.data);
      setState({
        error: false,
        success: true,
        msg: "Account Updated! Please sign back in again",
      });
      

      const timer = setTimeout(() => {

      setUser({
        isAuth: false,
        username: "",
        email: "",
        token: ""
      });
        navigate("/login");
      }, 2000);
    } catch (error) {
      setState({ success: false, error: true, msg: error.response.data });
      console.log(error.response.data);
    }
  };


  const getInfo = async () => {
    try {
      console.log(user);
      const res = await axios.get(
        process.env.REACT_APP_JAVA_API + "/users/getInfo/" + user.username,
        {
          withCredentials: true,
          headers: {
            Authorization: user.token,
          },
        }
      );
      setStartDate(new Date(res.data.birthDate));
      setCountry({
        label: res.data.country,
        value: countryList().getValue(res.data.country),
      });
      setForm(res.data);
    } catch (error) {
      console.log(error.response.data);
    }
  };
  const edit = () => {
    if (editState) setEditState(false);
    else setEditState(true);
  };
  const cancel = () => {
    navigate("/");
  };


  useEffect(() => {
    getInfo();
  }, []);

  return (
    <div className="containerFormReg">
      {!editState && (
        <div className="flex">
          <input
            type="button"
            className="flex-child btn bg-purple-600 
            txt-white-100 
            hover:bg-purple-900 
            btn-rounded"
            value="Edit"
            onClick={edit}
          />
        </div>
      )}
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
              disabled={!editState}
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
              disabled={!editState}
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
              disabled={!editState}
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
              value={form.gender}
              disabled={!editState}
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
              disabled={!editState}
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
              disabled={!editState}
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
              isDisabled={!editState}
              onChange={(c) => {
                setCountry(c);
              }}
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
              disabled={!editState}
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
              disabled={!editState}
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
              disabled={!editState}
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
              disabled={!editState}
            />
          </div>
        </div>
        <br />
        {editState && (
          <div className="flex">
            <input
              type="submit"
              className="flex-child btn bg-purple-600 
            txt-white-100 
            hover:bg-purple-900 
            btn-rounded"
              value="Update"
            />

            <input
              type="button"
              className="flex-child btn bg-purple-600 
            txt-white-100 
            hover:bg-purple-900 
            btn-rounded"
              value="Cancel"
              onClick={cancel}
            />
          </div>
        )}
      </form>
    </div>
  );
};

export default AccountComponent;
