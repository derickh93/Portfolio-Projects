using LaundryPickupNYC.Services;
using LaundryPickupNYC.ViewModels;
using Stripe;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class PastOrders : ContentPage
    {
        StripeList<Stripe.Invoice> invoices;
        CurrentOrderViewModel covm;

        public string Date { set; get; }
        public string Total { set; get; }
        public string Status { set; get; }


        public PastOrders()
        {
            InitializeComponent();

            covm = new CurrentOrderViewModel();


            invoices = StripeCheckout.invoiceList("paid");


            List<string> list = new List<string>();
            for (int i = 0; i < invoices.Data.Count; i++)
            {
                Date = "Order Date: " + invoices.ElementAt(i).Created.ToShortDateString();
                Total = " Order Total $" + invoices.ElementAt(i).Total * .01;
                Status = "Payment Status: " + invoices.ElementAt(i).Status.ToString();
                covm.CurrentOrderItems.Add(new Models.CurrentOrderItem(Date, Total, Status));
            }
            listInvoice.ItemsSource = covm.CurrentOrderItems.ToList();
            listInvoice.ItemSelected += AddressList_ItemSelected;
        }

        private async void AddressList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            var i = (listInvoice.ItemsSource as List<string>).IndexOf(e.SelectedItem as string);

            await Navigation.PushModalAsync(new NavigationPage(new InvoiceDetails(invoices.ElementAt(i))));
        }
        private async void NavigateBook_OnClicked(object sender, EventArgs e)
        {
            GlobalMethods.orderChangePopup();

            //if (GlobalVar.loggedIn == true)
            //{
            //    await Navigation.PushModalAsync(new NavigationPage(new Address()));
            //}
            //else
            //{
            //    await App.Current.MainPage.DisplayAlert("Alert", "Please login to place an order!", "OK");
            //    await Shell.Current.GoToAsync("//home");


            //}
        }
    }
}