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
    public partial class Profile : ContentPage
    {
        public Profile()
        {
            InitializeComponent();
            name.Text = GlobalVar.user.firstName + " " + GlobalVar.user.lastName;
            email.Text = GlobalVar.user.email;
            phone.Text = GlobalVar.user.phone;
        }

        private void address_Button_Clicked(object sender, EventArgs e)
        {
             Navigation.PushModalAsync(new NavigationPage(new AddressList()));

        }

        private void credit_Button_Clicked(object sender, EventArgs e)
        {
             Navigation.PushModalAsync(new NavigationPage(new CreditCardList()));
        }
    }
}