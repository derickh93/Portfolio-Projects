using LaundryPickupNYC.Views;
using Rg.Plugins.Popup.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SaveCard
    {
        public SaveCard()
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

        private async void Button_Clicked(object sender, System.EventArgs e)
        {
            StripeCheckout.nameOnCard = nameInput.Text;
            StripeCheckout.expDate = expInput.Text;
            StripeCheckout.ccNumber = ccInput.Text;
            StripeCheckout.cvv = cvvInput.Text;
            StripeCheckout.zipCode = zipInput.Text;
            StripeCheckout.saveCard();
            await PopupNavigation.PopAsync();
        }

    }
}