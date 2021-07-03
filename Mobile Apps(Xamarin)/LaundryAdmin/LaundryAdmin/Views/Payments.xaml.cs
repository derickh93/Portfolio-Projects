using LaundryAdmin.ViewModels;
using Stripe;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryAdmin.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Payments : ContentPage
    {
        StripeList<PaymentIntent> paymentIntents;
        PaymentViewModel pvm;
        public Payments()
        {
            InitializeComponent();


            pvm = new PaymentViewModel();
            var options = new PaymentIntentListOptions
            {
            };
            var service = new PaymentIntentService();
            paymentIntents = service.List(
              options
            );
            for (int i = 0; i < paymentIntents.Data.Count; i++)
            {
                pvm.PayItems.Add(new Models.PaymentItem(paymentIntents.Data.ElementAt(i).Amount.ToString(), paymentIntents.Data.ElementAt(i).Created.ToString(), paymentIntents.Data.ElementAt(i).CustomerId, paymentIntents.Data.ElementAt(i).Status));
            }

            paymentList.ItemsSource = pvm.PayItems;

        }
    }
}