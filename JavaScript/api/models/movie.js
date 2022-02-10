const mongoose = require('mongoose')

const movieSchema = new mongoose.Schema({
  title: {
    type: String,
    required: true,
  },
  director: {
    type: String,
    required: true,
  },
  date_added: {
    type: String,
    required: true,
  },
  release_year: {
    type: String,
    required: true,
  },
  rating: {
    type: String,
    required: true,
  },
  category: {
    type: String,
    required: true,
  }
},
{collection:"movieCollection"});
module.exports = mongoose.model('Movies',movieSchema)