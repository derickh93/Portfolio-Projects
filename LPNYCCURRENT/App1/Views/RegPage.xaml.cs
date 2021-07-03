using Firebase.Auth;
using Newtonsoft.Json;
using Stripe;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using Application = Xamarin.Forms.Application;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class RegPage : ContentPage
    {
        public string WebAPIkey = "AIzaSyAtDKWefoXXGlxMJN9x6MYy9nzgv1WMWag";
        readonly FirebaseHelper firebaseHelper = new FirebaseHelper();


        public RegPage()
        {
            InitializeComponent();
            this.UserNewEmail.ReturnCommand = new Command(() => this.UserNewPassword.Focus());
            this.UserNewPassword.ReturnCommand = new Command(() => this.UserNewFirst.Focus());

            this.UserNewFirst.ReturnCommand = new Command(() => this.UserNewLast.Focus());

            this.UserNewLast.ReturnCommand = new Command(() => this.UserNewPhone.Focus());


            UserNewPhone.Completed += (object sender, EventArgs e) =>
            {
                signupbutton_Clicked(sender, e);
            };
        }

        private async void addNewUser(string userID,string customerID)
        {
            await firebaseHelper.AddPerson(UserNewFirst.Text, UserNewLast.Text, UserNewEmail.Text, UserNewPhone.Text,userID,customerID);
        }

        private async void getUser(string userID)
        {
            GlobalVar.user = await firebaseHelper.GetPerson(userID);
        }

        async void signupbutton_Clicked(System.Object sender, System.EventArgs e)
        {
            try
            {
                var authProvider = new Firebase.Auth.FirebaseAuthProvider(new FirebaseConfig(WebAPIkey));
                var auth = await authProvider.CreateUserWithEmailAndPasswordAsync(UserNewEmail.Text, UserNewPassword.Text);
                string gettoken = auth.FirebaseToken;
                var user = auth.User;

                var options = new CustomerCreateOptions
                {
                    Email = UserNewEmail.Text,
                    Name = UserNewFirst.Text + UserNewLast.Text,
                    Phone = UserNewPhone.Text,
                    PaymentMethod = null,
                    InvoiceSettings = new CustomerInvoiceSettingsOptions
                    {
                        DefaultPaymentMethod = null,
                    },
                };
                var service = new CustomerService();
                Customer customer = service.Create(options);
                addNewUser(user.LocalId,customer.Id);
                GlobalVar.loggedIn = true;
                GlobalVar.user.customerID = customer.Id;
                getUser(user.LocalId);
                await App.Current.MainPage.DisplayAlert("Alert", "Successfully Created Account!", "OK");
                App.Current.MainPage = new AppShellLogged();

            }
            catch (Exception ex)
            {
                await App.Current.MainPage.DisplayAlert("Alert",ex.Message, "OK");
            }
        }

        async void loginbutton_Clicked(System.Object sender, System.EventArgs e)
        {
            var authProvider = new FirebaseAuthProvider(new FirebaseConfig(WebAPIkey));
            try
            {
                var auth = await authProvider.SignInWithEmailAndPasswordAsync(UserNewEmail.Text, UserNewPassword.Text);
                var content = await auth.GetFreshAuthAsync();
                var serializedcontnet = JsonConvert.SerializeObject(content);
                Preferences.Set("MyFirebaseRefreshToken", serializedcontnet);
                await Navigation.PushAsync(new Booking());
            }
            catch (Exception ex)
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Invalid useremail or password", "OK");
            }
        }
    }
}