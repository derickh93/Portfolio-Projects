using System;
using System.ComponentModel;
using Xamarin.Forms;

namespace LaundryPickupNYC.Views
{
    [DesignTimeVisible(false)]
    public partial class CreditCardPage : ContentPage
    {
        public CreditCardPage()
        {
            InitializeComponent();
            this.nameInput.ReturnCommand = new Command(() => this.ccInput.Focus());

            this.ccInput.ReturnCommand = new Command(() => this.expInput.Focus());

            this.expInput.ReturnCommand = new Command(() => this.cvvInput.Focus());

            this.cvvInput.ReturnCommand = new Command(() => this.zipInput.Focus());

            zipInput.Completed += (object sender, EventArgs e) =>
            {
                Button_Clicked(sender, e);
            };
        }

        private void Button_Clicked(object sender, System.EventArgs e)
        {
            StripeCheckout.nameOnCard = nameInput.Text;
            StripeCheckout.expDate = expInput.Text;
            StripeCheckout.ccNumber = ccInput.Text;
            StripeCheckout.cvv = cvvInput.Text;
            StripeCheckout.zipCode = zipInput.Text;

            //Navigation.PushModalAsync(new NavigationPage(new ReviewPage()));
        }
    }
}
