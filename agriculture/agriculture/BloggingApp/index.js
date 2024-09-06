const express = require("express")
const mongoose = require("mongoose")
const dotenv = require("dotenv")
const cors = require("cors")
const axios = require("axios")
const blog = require("./models/Post")
const eurekaHelper = require("./eureka-helper")
const port = 4002
const app = express();

dotenv.config();
app.use(cors('*'))
app.use(express.json());
mongoose.connect(process.env.MONGO_URL, {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(console.log("Connected to mongoDB"))
    .catch((err) => console.log(err))

// app.get("/blog",(req,res)=>{
//     res.send("This is blogging Service app")
// })


app.post("/blog/add", async (req, res) => {
    try {
        console.log(req.body)
        var uid = parseInt(req.body.id)
        var userInfo = await axios.get(`http://127.0.0.1:8081/user/blog/${uid}`)
       // console.log(userInfo)
        //  var imagePath = await axios.post("http://localhost:4001/imageHandler/blog",req.body.blogImage)
        const newBlog = new blog({
            title: req.body.title,
            userFirstName: userInfo.data.firstname,
            userLastName: userInfo.data.lastname,
            userId: req.body.id,
            tags: req.body.tags.split("#"),
            desc: req.body.desc
        });
        const savedBlog = await newBlog.save();
        res.status(200).json(savedBlog)
    } catch (err) {
        res.status(500).json(err)
    }
})

app.get("/blog", async (req, res) => {
    try {
        const result = await blog.find();
        res.status(200).json(result);
    } catch (err) {
        console.log(err)
    }
})


app.get("/blog/:id", async (req, res) => {
    try {
        const result = await blog.find({ userId: req.params.id });
        res.json(result)
    } catch (err) {
        console.log(err)
    }
})

// app.delete("/blog/delete/:id",(req,res)=>{
//     res.json(blog.deleteOne({"_id":req.params.id}))
// })

app.listen(port, () => {
    console.log(`bloggingApp is started and is running on port no. : ${port} `)
})

eurekaHelper.registerWithEureka("BloggingService",4002)