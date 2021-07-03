using LaundryPickupNYC.Views;
using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC
{
    public partial class App : Application
    {

        public App()
        {
            Syncfusion.Licensing.SyncfusionLicenseProvider.RegisterLicense("NDQzMDE5QDMxMzkyZTMxMmUzMFZqcWlLdXZpaUN3endERzczRVZGUSswVHZuSTU5N09Qd3J5dFhZRFphdUE9");

            InitializeComponent();
            Device.SetFlags(new[] { "Swipeview_Experimental" });
            MainPage = new AppShell();
            var barBackgroundColorSetter = new Setter { Property = NavigationPage.BarBackgroundColorProperty, Value = Color.FromHex("#1c2f74") };
            //var barTextColorSetter = new Setter { Property = NavigationPage.BarTextColorProperty, Value = Color.White };
        }

        protected override void OnStart()
        {
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }
    }
}
