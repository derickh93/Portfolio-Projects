using Stripe;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ReviewPage : ContentPage
    {
        PaymentMethod pm;
        public ReviewPage(PaymentMethod pm)
        {
            InitializeComponent();
            address.Text = "ADDRESS: " + Object_Classes.Transaction.address.ToString();
            specialOrder.Text = "SPECIAL INSTRUCTIONS: " + Object_Classes.Transaction.specialOrder;
            orderQuantity.Text = "LAUNDRY QUANTITY: " + Object_Classes.Transaction.orderQuantity;
            pickupTime.Text = "PICKUP TIME: " + Object_Classes.Transaction.pickupTime + " to " + Object_Classes.Transaction.pickupTime.AddHours(3).ToShortTimeString();
            dropoffTime.Text = "DROP OFF TIME: " + Object_Classes.Transaction.dropoffTime + " to " + Object_Classes.Transaction.dropoffTime.AddHours(3).ToShortTimeString();
            text.Text = "Text: " + Object_Classes.Transaction.textDrop;
            call.Text = "Call: " + Object_Classes.Transaction.callDrop;
            ring.Text = "Ring: " + Object_Classes.Transaction.ringDrop;

            this.pm = pm;
        }
        private void Button_Clicked(object sender, EventArgs e)
        {
            //StripeCheckout.PayViaStripePayment(pm);
            var output = StripeCheckout.createInvoice();
            Navigation.PushModalAsync(new NavigationPage(new ConfirmationPage(output.Number)));

        }
    }
}