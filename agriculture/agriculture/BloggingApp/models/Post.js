const mongoose = require("mongoose")

const PostSchema = new mongoose.Schema(
    {
        title:{
            type:String,
            required:true,
            unique:true,
        },
        userFirstName:{
            type: String,
            required: true,
        },
        userLastName:{
            type: String,
            required: true,
        },
        userId:{
            type: Number,
            required: true,
        },
        tags:{
            type:Array,
            require:false
        },
        desc:{
            type: String,
            required: true, 
        },
    },
        {
            timestamps: true
        }
);

module.exports = mongoose.model("Post",PostSchema);