using LaundryPickupNYC.Object_Classes;
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
    public partial class ConfirmationPage : ContentPage
    {
        public ConfirmationPage(string recNum)
        {
            InitializeComponent();
            confMsg.Text = "Your order was a success " + GlobalVar.user.firstName+ ". We will see you on " + Transaction.pickupTime.Day.ToString() + ", " + Transaction.pickupTime.ToShortDateString()
                + " at " + Transaction.pickupTime.ToShortTimeString();
            confNum.Text = "Receipt Number: " + recNum;
            
        }
    }
}