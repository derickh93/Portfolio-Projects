using LaundryPickupNYC.Services;
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
    public partial class Pricing : ContentPage
    {
        public Pricing()
        {
            InitializeComponent();
        }

        private async void NavigateBook_OnClicked(object sender, EventArgs e)
        {

            //GlobalMethods.orderChangePopup();
            if (GlobalVar.loggedIn == true)
            {
                await Navigation.PushModalAsync(new NavigationPage(new Address()));
            }
            else
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Please login to place an order!", "OK");
                await Shell.Current.GoToAsync("//home");


            }
        }

        private async void NavigateWash_OnClicked(object sender, EventArgs e)
        {
            //await Shell.Current.GoToAsync("//washandfold");
            var button = (Button)sender;
            var classId = button.ClassId;
            await Navigation.PushModalAsync(new NavigationPage(new WashnFold(classId)));
        }
    }
}