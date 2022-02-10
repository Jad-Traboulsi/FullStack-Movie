const express = require("express");
const bcrypt = require("bcrypt");
const { validEmail, validPassword } = require("../utils/valid");

const userModel = require("../models/user");

let router = express.Router();

router.post("/register", async (req, res) => {
  try {
    const { email, email_cfg, password, password_cfg, username } = req.body;
    let errors = {
      email: [],
      email_cfg: [],
      password: [],
      password_cfg: [],
    };
    if (email != email_cfg) {
      errors.email.push("email mismatch");
      errors.email_cfg.push("email mismatch");
    }

    if (password != password_cfg) {
      errors.password.push("password mismatch");
      errors.password_cfg.push("password mismatch");
    }

    errors = validEmail(errors, email);
    errors = validPassword(errors, password);

    if (
      errors.email.length > 0 ||
      errors.email_cfg.length > 0 ||
      errors.password.length > 0 ||
      errors.password_cfg > 0
    ) {
      return res.status("500").json(errors);
    }

    // crypt the pass
    const saltRounds = 10;

    const salt = bcrypt.genSaltSync(saltRounds);
    const hash = bcrypt.hashSync(password, salt);
    const user = await userModel.findOne({ email: email });

    if (!user) {
      await userModel.create({
        email: email,
        password: hash,
        username: username,
      });

      return res.status("200").json({ message: "Created" });
    } else {
      return res.status("500").json({ message: "email already exists" });
    }
  } catch (error) {
    console.error(error);
    return res.status("500").json(error.message);
  }
});

router.post("/login", async (req, res) => {
  try {
    const { email, password} = req.body;
    let errors={
      email:[],
      password:[]
    };
   

    errors = validEmail(errors, email);
    errors = validPassword(errors, password);

    if (errors.email.length > 0 || errors.password.length>0) {
      return res.status("500").json(errors);
    }

    const user = await userModel.findOne({ email: email });

    if (!user) {
      return res.status("500").json({ message: "email doesnt exist" });
    } else if (!bcrypt.compareSync(password, user.password)) {
      return res.status("500").json({ message: "wrong password" });
    } else {
      req.session.user = user
      return res.status("200").json({message:"Log in Successful",user});
    }
  } catch (error) {
    console.error(error);
    return res.status("500").json(error.message);
  }
});


router.get("/logout", async (req, res) => {
  try{
  let user = req.session.user
  console.log(user);
  
  if (!user) {
      return res.status("500").json({ message: "Not signed in" });
    
    } else {
      req.session.destroy()
      return res.status("200").json({ message: "Log out Successful" });
    }
  } catch (error) {
    console.error(error);
    return res.status("500").json(error.message);
  }
});
 
router.get("/me", (req, res) => {
  try {
    if (req.session && req.session.user) {
      return res.status(200).json(req.session.user);
    } else {
      return res.status(500).json({ msg: "You arent logged in" });
    }
  } catch (error) {
    return res.status(500).json(error.message);
  }
});

module.exports = router;
