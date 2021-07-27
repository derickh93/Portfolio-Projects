var express = require('express');
const stripe = require('stripe')('sk_test_51Hi7bWEkFqXnuEeNT4n9wpUsPAXBhFX55VP6fnvzLzMLM582cpeqGUpRisqCsbXcQeQsl2I5b4PSm3HhJGHE7sb900HatYoqAQ');
var app = express();

app.listen(3000, function () {
    console.log("server running");
});

//create new customer

var createCustomer = function () {
    var param ={};
    param.email ="mike@gmail.com";
    param.name="Mike";
    param.description ="from node";

    stripe.customers.create(param, function (err,customer) {
        if(err)
        {
            console.log("err: "+err);
        }if(customer)
        {
            console.log("success: "+customer)
        }else{
            console.log("Something wrong")
        }
    })

}

createCustomer();