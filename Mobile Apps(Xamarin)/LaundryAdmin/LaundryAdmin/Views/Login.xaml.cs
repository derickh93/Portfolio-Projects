using App1;
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

namespace LaundryAdmin.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Login : ContentPage
    { 
        public string WebAPIkey = "AIzaSyAtDKWefoXXGlxMJN9x6MYy9nzgv1WMWag";
    readonly FirebaseHelper firebaseHelper = new FirebaseHelper();
    int tapCount = 0;

    public Login()
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
    }
    async void loginbutton_Clicked(System.Object sender, System.EventArgs e)
    {
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

            App.Current.MainPage = new AppShell();
        }
        catch (Exception ex)
        {
            await App.Current.MainPage.DisplayAlert("Alert", "Invalid email or password", "OK");
        }
    }

}
}