const bodyParser = require("body-parser")
const express = require("express")
const morgan = require("morgan")
const helmet = require("helmet")
const session = require("express-session")
const cors = require('cors')

require('./utils/data')

const movieRoute = require('./routes/movies')

const app = express()

app.use(morgan('dev'))
app.use(helmet())
app.use(
  cors({
    credentials: true,
    origin: ["http://localhost:3000"],
  })
);

app.use(bodyParser.json({
    extended:true
}))

app.use(bodyParser.urlencoded({ extended: false }));

app.use(session({
    path:'/',
    httpOnly:true,
    secure:true,
    maxAge:null,
    secret:"Heyy"
}))

app.use("/movies",movieRoute)


app.listen(5000,() => {
    console.log("Server running on http://localhost:5000");
})