const mongoose = require('mongoose');

mongoose.connect(

    'mongodb://localhost:27117/Movies',
    {},
    (error)=>{
        if(error) throw error
        console.log("mongo connected");
    }
)