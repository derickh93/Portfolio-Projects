using Stripe;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Text;
using Xamarin.Forms;

namespace LaundryPickupNYC
{
    public static class StripeCheckout
    {
        public static string nameOnCard;
        public static string ccNumber;
        public static string expDate;
        public static string cvv;
        public static string zipCode;

        public static StripeList<PaymentMethod> loadCards()
        {
            StripeConfiguration.ApiKey = "pk_test_51Hi7bWEkFqXnuEeNPS2AM0R1240RpwhpKqKch8Ywu8GvkHEBPd7sgnXW9gikdqaaTK9c2fWebMfe1VKK3TO7igeW000LknCLVS";
            var options = new PaymentMethodListOptions
            {
                Customer = GlobalVar.user.customerID,
                Type = "card",
            };
            var service = new PaymentMethodService();
            StripeList<PaymentMethod> paymentMethods = service.List(
              options
            );
            return paymentMethods;

        }

        public async static void saveCard()
        {
            try
            {
                string cardno = ccNumber;
                int expMonth = int.Parse(expDate.Substring(0, expDate.IndexOf('/')));
                int expYear = int.Parse(expDate.Substring(expDate.IndexOf('/') + 1));
                string cardCvv = cvv;


                var options = new TokenCreateOptions
                {
                    Card = new TokenCardOptions
                    {
                        Number = cardno,
                        ExpMonth = expMonth,
                        ExpYear = expYear,
                        Cvc = cardCvv,
                    },
                };


                // step 2: Assign card to token object
                TokenCreateOptions stripeCard = new TokenCreateOptions();
                stripeCard = options;

                TokenService service = new TokenService();
                Token newToken = service.Create(stripeCard);


                // step 3: assign the token to the source
                var option = new SourceCreateOptions
                {
                    Type = SourceType.Card,
                    Currency = "USD",
                    Token = newToken.Id
                };

                var sourceService = new SourceService();
                Source source = sourceService.Create(option);

                //save card
                ///////////////////////////////////////////
                var options2 = new CardCreateOptions
                {
                    Source = source.Id,
                };
                var service2 = new CardService();
                Card output = service2.Create(GlobalVar.user.customerID, options2);
            }
            catch (Exception ex)
            {
                if (ex.Message.Equals("Object reference not set to an instance of an object."))
                    await App.Current.MainPage.DisplayAlert("Alert", "Please complete all fields", "Ok");
                else
                {
                    await App.Current.MainPage.DisplayAlert("Alert", ex.Message.ToString(), "Ok");
                }
            }

        }

        public static void PayViaStripePayment(PaymentMethod pm)
        {

            try
            {
                var options = new PaymentIntentCreateOptions
                {
                    Amount = 100,
                    Currency = "usd",
                    Customer = GlobalVar.user.customerID,
                    PaymentMethodTypes = new List<string>
              {
                "card",
              },
                };

                var service = new PaymentIntentService();
                var pi = service.Create(options);

                var options2 = new PaymentIntentConfirmOptions
                {
                    PaymentMethod = pm.Id,

                };
                var service2 = new PaymentIntentService();
                service2.Confirm(
                  pi.Id,
                  options2
                );
            }
            catch (Exception ex)
            {
                App.Current.MainPage.DisplayAlert("Alert", ex.Message.ToString(), "Ok");
            }

        }

        public async static void deleteCard(string card_ID)
        {
            try
            {
                var serviceDel = new CardService();
                serviceDel.Delete(
                GlobalVar.user.customerID,
                card_ID
                );
            }
            catch (Exception ex)
            {
                await App.Current.MainPage.DisplayAlert("Alert", ex.Message.ToString(), "Ok");
            }
        }

        public static  Stripe.Invoice createInvoice()
        {
            Stripe.Invoice output = new Stripe.Invoice();
            try
            {
                invoiceItem();
                var options = new InvoiceCreateOptions
                {
                    Customer = GlobalVar.user.customerID,
                    Description = "Thank you for your business.",
                    StatementDescriptor = "Hope to see you again.",
                    Metadata = new Dictionary<string, string>
                {{"Status","Scheduled"}, {"Address",Object_Classes.Transaction.address.ToString()},
             { "SPECIAL INSTRUCTIONS: ",Object_Classes.Transaction.specialOrder },
                        {"LAUNDRY QUANTITY: ", Object_Classes.Transaction.orderQuantity},
                        {"PICKUP TIME:",Object_Classes.Transaction.pickupTime.ToString()}, 
                        {"DROP OFF TIME:", Object_Classes.Transaction.dropoffTime.ToString()},
                        { "Text:", Object_Classes.Transaction.textDrop.ToString() },
                        { "Call:", Object_Classes.Transaction.callDrop.ToString() },
                        {"Ring:", Object_Classes.Transaction.ringDrop.ToString() }
                }
                };
                var service = new InvoiceService();
                output = service.Create(options);
            }
            catch (Exception ex)
            {
                App.Current.MainPage.DisplayAlert("Alert", ex.Message.ToString(), "Ok");
            }
            return output;
        }

        public static void invoiceItem()
        {
            var options = new InvoiceItemCreateOptions
            {
                Customer = GlobalVar.user.customerID,
                Price = "price_1I8XnxEkFqXnuEeN9dJk9ayW",
            };
            var service = new InvoiceItemService();
            service.Create(options);
        }

        public static StripeList<Stripe.Invoice> invoiceList(string type) {
            StripeList<Stripe.Invoice> invoices;
            var options = new InvoiceListOptions
            {
                Customer = GlobalVar.user.customerID,
                Status = type
            };
            var service = new InvoiceService();
            invoices = service.List(
              options
            );
            return invoices;
        }
        public static void uploadFile()
        {
            try
            {

                var folderPath = DependencyService.Get<ICaminho>().GetPath();

                var filePath = Path.Combine(folderPath, "sample.txt");
                System.IO.File.WriteAllText(filePath, "text");

                using (FileStream stream = System.IO.File.Open(filePath, FileMode.Open))
                {
                    var options = new FileCreateOptions
                    {
                        File = stream,
                        Purpose = FilePurpose.AdditionalVerification
                    };
                    var service = new FileService();
                    var file = service.Create(options);
                }
            }
            catch (Exception ex)
            {
                App.Current.MainPage.DisplayAlert("Alert", ex.Message.ToString(), "Ok");
            }
        }

    }
}
