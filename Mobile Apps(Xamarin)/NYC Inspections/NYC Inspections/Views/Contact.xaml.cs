using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace NYC_Inspections.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Contact : ContentPage
    {
        public bool forwardEmail = false;
        public static Action EmulateBackPressed;

        private bool AcceptBack;
        public Contact()
        {
            InitializeComponent();

            this.Name.ReturnCommand = new Command(() => this.Email.Focus());
            this.Email.ReturnCommand = new Command(() => this.Subject.Focus());

            this.Subject.ReturnCommand = new Command(() => this.Message.Focus());

            Message.Completed += (object sender, EventArgs e) =>
            {
                OnImageEmailTapped(sender, e);
            };
        }

        private void OnImageEmailTapped(object sender, EventArgs args)
        {
            List<String> recipients = new List<String>();
            recipients.Add("qssdevops@gmail.com");
            if (forwardEmail)
            {
                recipients.Add(Email.Text.ToString());
            }
            try
            {
                SendEmail(Subject.Text.ToString(), Message.Text.ToString(), recipients);
            }
            catch (Exception ex)
            {
                DisplayAlert("Error","Fill form completely","Close");
            }
        }

        public async Task SendEmail(string subject, string body, List<string> recipients)
        {
            try
            {
                var message = new EmailMessage
                {
                    Subject = subject,
                    Body = body,
                    To = recipients,
                    //Cc = ccRecipients,
                    //Bcc = bccRecipients
                };
                await Xamarin.Essentials.Email.ComposeAsync(message);
            }
            catch (FeatureNotSupportedException fbsEx)
            {
                // Email is not supported on this device
            }
            catch (Exception ex)
            {
                // Some other exception occurred
                DisplayAlert("Error", "Invalid Email", "Close");

            }
        }

        void OnCheckBoxCheckedChanged(object sender, CheckedChangedEventArgs e)
        {
            // Perform required operation after examining e.Value
                forwardEmail = !forwardEmail;
        }

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