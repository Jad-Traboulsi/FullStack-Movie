import React from "react";
import AccountComponent from "../components/AccountComponent";
import userState from "../atoms/userAtoms";
import { useRecoilState } from "recoil";
import { Link } from "react-router-dom";

const Account = () => {
  const [user, setUser] = useRecoilState(userState);
  return (
    <>
      {user.isAuth && (
        <div>
          <div className="title">Account Details</div>
          <br />
          <AccountComponent />
        </div>
      )}
      {!user.isAuth && (
        <div className="error">
          <span>
            You arent logged in
            <br />
            <br />
            <br />
            <Link to="/login">Log In</Link>
          </span>
        </div>
      )}
    </>
  );
};

export default Account;
