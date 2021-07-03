using LaundryPickupNYC.Views;
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
    public partial class AppShell : Xamarin.Forms.Shell
    {
        public AppShell()
        {
            InitializeComponent();
            Shell.SetTabBarIsVisible(this, false);
            Navigation.PushModalAsync(new NavigationPage(new SplashScreen()));

        }

        public static Action EmulateBackPressed;

        private bool AcceptBack;

        protected override bool OnBackButtonPressed()
        {
            if (AcceptBack)
                return false;

            PromptForExit();
            return true;
        }

        private async void PromptForExit()
        {
            if (await DisplayAlert("", "Are you sure to exit?", "Yes", "No"))
            {
                AcceptBack = true;
                System.Diagnostics.Process.GetCurrentProcess().Kill();
            }
        }
    }
}