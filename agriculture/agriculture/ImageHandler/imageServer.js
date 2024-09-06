const multer = require("multer")
const express = require("express")
const eurekaHelper = require("./eureka-helper")
const axios = require("axios")

const app = express()

const storage = multer.diskStorage({
    destination:(req,res,callBack)=>{
        callBack(null,"/var/www/html")
    },
    filename:(req,file,callBack)=>{
        callBack(null,file.originalname + '-' + Date.now())
    }
})

const blogStorage = multer.diskStorage({
    destination:(req,res,callBack)=>{
        callBack(null,"/var/www/html/blog")  ///var/www/html/blog
    },
    filename:(req,file,callBack)=>{
        callBack(null,file.originalname + '-' + Date.now())
    }
})

const saveImage = multer({storage:storage})

const blogImage = multer({storage:blogStorage})


app.post("/imageHandler",saveImage.single("imageFile"),(req,res)=>{
  //  var pid = parseInt(req.body.id)
   
    let payload = {FileName : "http://172.18.6.56:80/"+req.file.filename , id : req.body.id}
    axios.post("http://172.18.6.56:8081/product/image",payload);
    res.end();

})

app.post("/imageHandler/blog",blogImage.single("blogImage"),(req,res)=>{
    res.status(200).json({imagePath:"http://127.0.0.1:80"+req.file.filename})
})
app.get("/imageHandler",(req,res)=>
{
    res.send("Welcome Home");
})

app.listen(4001, function(err){
    if (err) console.log(err);
    console.log("Server listening on PORT", 4001);
});
eurekaHelper.registerWithEureka("ImageHandler",4001)
