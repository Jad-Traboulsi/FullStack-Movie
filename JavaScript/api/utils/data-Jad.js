const mongoose = require('mongoose');

mongoose.connect(

    'mongodb://localhost:27117/epita-adv',
    {},
    (error)=>{
        if(error) throw error
        console.log("mongo connected");
    }
)