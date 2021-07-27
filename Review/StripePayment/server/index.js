const express = require("express");
const app = express();
require("dotenv").config();
const stripe = require("stripe")(process.env.STRIPE_SECRET_TEST);
const bodyParser = require("body-parser");
const cors = require("cors");

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.use(cors());

app.post("/payment", cors(), async (req, res) => {
  let { amount, id, cid } = req.body;
  try {
    const payment = await stripe.paymentIntents.create({
      amount,
      currency: "USD",
      description: "Spatula company",
      payment_method: id,
      confirm: true,
      setup_future_usage: "off_session",
      customer: cid,
    });
    console.log("Payment", payment);
    res.json({
      message: "Payment successful",
      stripeRes: payment,
      success: true,
    });
  } catch (error) {
    console.log("Error", error);
    res.json({
      message: "Payment failed",
      success: false,
    });
  }
});

//////////////////////////////////////////////////////
app.post("/create", cors(), async (req, res) => {
  try {
    let { firstName, lastName, phone, email } = req.body;
    const customer = await stripe.customers.create({
      email: email,
      phone: phone,
      name: firstName + " " + lastName,
    });
    console.log("Payment", customer);
    res.json({
      message: "Payment successful",
      stripeCust: customer,
      success: true,
    });
  } catch (error) {
    console.log("Error", error);
    res.json({
      message: "Payment failed",
      success: false,
    });
  }
});
/////////////////////////////////////////////////////

//////////////////////////////////////////////////////
app.post("/create-customer-portal-session", cors(), async (req, res) => {
  try {
    let { cid } = req.body;
    const session = await stripe.billingPortal.sessions.create({
      customer: cid,
      return_url: "https://localhost:3000",
    });

    //res.redirect(session.url);
    //console.log(session.url);

    res.json({
      message: "Portal URL Attached",
      portalURL: session.url,
      success: true,
    });
  } catch (error) {
    console.log("Error", error);
    res.json({
      message: "Portal failed",
      success: false,
    });
  }
});
/////////////////////////////////////////////////////

////////////////////////////////////////////////////

app.post("/add-address", cors(), async (req, res) => {
  try {
    let { cid, address, city, state, full_name } = req.body;
    const customer = await stripe.customers.update(cid, {
      shipping: {
        name: full_name,
        address: {
          line1: address,
          city: city,
          state: state,
          country: "US",
          postal_code: "94111",
        },
      },
    });

    res.json({
      message: "address added",
      success: true,
      result: customer,
    });
    console.log(customer);
  } catch (error) {
    console.log("Error", error);
    res.json({
      message: "address not added",
      success: false,
    });
  }
});
///////////////////////////////////////////////////

////////////////////////////////////////////////////

app.post("/get-customer", cors(), async (req, res) => {
  try {
    let { custID } = req.body;
    console.log(custID);
    const customer = await stripe.customers.retrieve(custID);

    res.json({
      message: "customer retrieved",
      success: true,
      result: customer,
    });
  } catch (error) {
    console.log("Error", error);
    res.json({
      message: "customer not retrieved",
      success: false,
    });
  }
});
///////////////////////////////////////////////////

////////////////////////////////////////////////////

app.post("/get-cards", cors(), async (req, res) => {
  try {
    let { custID } = req.body;
    console.log(custID);

    const paymentMethods = await stripe.paymentMethods.list({
      customer: custID,
      type: "card",
    });

    res.json({
      message: "cards retrieved",
      success: true,
      result: paymentMethods,
    });
  } catch (error) {
    console.log("Error", error);
    res.json({
      message: "cards not retrieved",
      success: false,
    });
  }
});
///////////////////////////////////////////////////
app.listen(process.env.PORT || 4000, () => {
  console.log("Sever is listening on port 4000");
});
