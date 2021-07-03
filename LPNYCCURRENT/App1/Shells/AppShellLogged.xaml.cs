using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Transactions;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class AppShellLogged : Xamarin.Forms.Shell
    {
        public AppShellLogged()
        {
            InitializeComponent();
            Shell.SetTabBarIsVisible(this, false);
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

        private async void OnMenuItemClicked(object sender, EventArgs e)
        {
            GlobalVar.loggedIn = false;
            GlobalVar.user = new Person();
            GlobalVar.loading = true;

            App.Current.MainPage = new AppShell();
            //await Shell.Current.GoToAsync("//LoginPage");
        }
    }
}