using Firebase.Auth;
using LaundryPickupNYC.PopupViews;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Newtonsoft.Json;
using Rg.Plugins.Popup.Services;
using Stripe;
using Stripe.BillingPortal;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Home : ContentPage
    {
        public string WebAPIkey = "AIzaSyAtDKWefoXXGlxMJN9x6MYy9nzgv1WMWag";
        readonly FirebaseHelper firebaseHelper = new FirebaseHelper();
        int tapCount = 0;

        public Home()
        {
            InitializeComponent();

            StripeConfiguration.ApiKey = "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";

            GlobalVar.loading = false;
                this.UserLoginEmail.ReturnCommand = new Command(() => this.UserLoginPassword.Focus());

                UserLoginPassword.Completed += (object sender, EventArgs e) =>
                {
                    loginbutton_Clicked(sender, e);
                };

        }

        async void signupbutton_Clicked(System.Object sender, System.EventArgs e)
        {
            try
            {
                    var authProvider = new Firebase.Auth.FirebaseAuthProvider(new FirebaseConfig(WebAPIkey));
                    var auth = await authProvider.CreateUserWithEmailAndPasswordAsync(UserLoginEmail.Text, UserLoginPassword.Text);
                    string gettoken = auth.FirebaseToken;
                    await App.Current.MainPage.DisplayAlert("Alert", gettoken, "Ok");
                
            }
            catch (Exception ex)
            {
                await App.Current.MainPage.DisplayAlert("Alert", ex.Message, "OK");
            }

        }

        private async void getUser(string userID)
        {
            GlobalVar.user = await firebaseHelper.GetPerson(userID);
            GlobalVar.user.custAddress = Newtonsoft.Json.JsonConvert.DeserializeObject<List<LaundryPickupNYC.Object_Classes.Address>>(GlobalVar.user.jsonList);
        }
        async void loginbutton_Clicked(System.Object sender, System.EventArgs e)
        {
            PopupNavigation.Instance.PushAsync(new LoadingPopup(), true);

            var authProvider = new FirebaseAuthProvider(new FirebaseConfig(WebAPIkey));
            try
            {
                string trimmedEmail = String.Concat(UserLoginEmail.Text.Where(c => !Char.IsWhiteSpace(c)));
                string trimmedPassword = String.Concat(UserLoginPassword.Text.Where(c => !Char.IsWhiteSpace(c)));

                var auth = await authProvider.SignInWithEmailAndPasswordAsync(trimmedEmail, trimmedPassword);
                var content = await auth.GetFreshAuthAsync();
                var serializedcontnet = JsonConvert.SerializeObject(content);
                Preferences.Set("MyFirebaseRefreshToken", serializedcontnet);
                GlobalVar.loggedIn = true;
                var user = auth.User;
                getUser(user.LocalId);

                App.Current.MainPage = new AppShellLogged();
                PopupNavigation.PopAllAsync();
            }
            catch (Exception ex)
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Invalid email or password", "OK");
            }
        }

        async void signupbutton(object sender, EventArgs args)
        {
            try
            {
                await Shell.Current.GoToAsync("//register");


            }
            catch (Exception ex)
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Invalid useremail or password" + ex, "OK");
            }
        }

       async void OnTapGestureRecognizerTapped(object sender, EventArgs args)
        {
            try
            {
                await Shell.Current.GoToAsync("//resetpw");


            }
            catch (Exception ex)
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Invalid useremail or password" + ex, "OK");
            }
        }

    }
}