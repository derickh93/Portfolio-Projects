using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Contact_US : ContentPage
    {
        public Contact_US()
        {
            InitializeComponent();

        }

        void OnImageEmailTapped(object sender, EventArgs args)
        {
            List<String> recipients = new List<String>();
            recipients.Add("washgolaundry@gmail.com");
            try
            {
                SendEmail("Laundry Pickup NYC Inquiry","", recipients);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        void OnImagePhoneTapped(object sender, EventArgs args)
        {
            try
            {
                Launcher.OpenAsync(new Uri("tel:3476889274"));
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        void OnImageTextTapped(object sender, EventArgs args)
        {
            try
            {
                SendSms("", "3476889274");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public async Task SendSms(string messageText, string recipient)
        {
            try
            {
                var message = new SmsMessage(messageText, new[] { recipient });
                await Sms.ComposeAsync(message);
            }
            catch (FeatureNotSupportedException ex)
            {
                // Sms is not supported on this device.
            }
            catch (Exception ex)
            {
                // Other error has occurred.
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
                await Email.ComposeAsync(message);
            }
            catch (FeatureNotSupportedException fbsEx)
            {
                // Email is not supported on this device
            }
            catch (Exception ex)
            {
                // Some other exception occurred
            }
        }
    }
}