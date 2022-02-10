const express = require("express");
const movieModel = require("../models/movie");

let router = express.Router();


router.get("/getId/:movieId", async (req, res) => {
  try {
    let movie = await movieModel.findOne({ _id: req.params.movieId });
    res.status("200").json(movie);
  } catch (error) {
    console.error(error);
    res.status("500").json(error.message);
  }
});

router.get("/getAll", async (req, res) => {
  try {
    console.log(req.params.messageId);
    let movie = await movieModel.find({});
    res.status("200").json(movie);
  } catch (error) {
    console.error(error);
    res.status("500").json(error.message);
  }
});

router.post("/addMovie", async (req, res) => {
  try {
    const { title,director,date_added,release_year,rating,category } = req.body;
    let movie = await movieModel.create({
      title: title,
      director: director,
      date_added: date_added,
      release_year: release_year,
      rating: rating,
      category: category
    });
    console.log(movie);
    res.status("200").json(movie);
  } catch (error) {
    console.error(error);
    res.status("500").json(error.message);
  }
});

module.exports = router;
