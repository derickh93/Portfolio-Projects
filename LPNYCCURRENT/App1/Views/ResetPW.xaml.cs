using Firebase.Auth;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views

{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ResetPW : ContentPage
    {
        public string WebAPIkey = "AIzaSyAtDKWefoXXGlxMJN9x6MYy9nzgv1WMWag";

        public ResetPW()
        {
            InitializeComponent();

            UserResetEmail.Completed += (object sender, EventArgs e) =>
            {
                resetButton_Clicked(sender, e);
            };
        }

        private void resetButton_Clicked(object sender, EventArgs e)
        {
            string trimmedEmail = "";
            try
            {
                trimmedEmail = String.Concat(UserResetEmail.Text.Where(c => !Char.IsWhiteSpace(c)));
            }
            catch
            {
                trimmedEmail = "invalid";
            }
            if (!IsValidEmail(trimmedEmail))
            {
                App.Current.MainPage.DisplayAlert("Alert", "Invalid Email Entered", "OK");
            }
            else
            {
                var authProvider = new FirebaseAuthProvider(new FirebaseConfig(WebAPIkey));
                authProvider.SendPasswordResetEmailAsync(trimmedEmail);
                App.Current.MainPage.DisplayAlert("Alert", "If the account exist an email will be sent to reset the password!", "OK");
                Shell.Current.GoToAsync("//home");
            }
        }

        public bool IsValidEmail(string emailaddress)
        {
            try
            {
                MailAddress m = new MailAddress(emailaddress);

                return true;
            }
            catch (FormatException)
            {
                return false;
            }
        }
    }
}