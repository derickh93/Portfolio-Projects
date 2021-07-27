using LaundryPickupNYC.Models;
using LaundryPickupNYC.Services;
using LaundryPickupNYC.ViewModels;
using Stripe;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Internals;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class CurrentOrders : ContentPage
    {
        List<Stripe.Invoice> openInvoices;
        List<Stripe.Invoice> draftInvoices;
        List<Stripe.Invoice> combinedInvoices;
        CurrentOrderViewModel covm;

        public string Date { set; get; }
        public string Total { set; get; }
        public string Status { set; get; }

        public CurrentOrders()
        {
            InitializeComponent();
            covm = new CurrentOrderViewModel();

            openInvoices = StripeCheckout.invoiceList("open").ToList();
            draftInvoices = StripeCheckout.invoiceList("draft").ToList();
            combinedInvoices = openInvoices.Concat(draftInvoices).ToList();

            //Order the contacts
            combinedInvoices = combinedInvoices.OrderByDescending(x => x.Created)
                                  .ToList();

            for(int i = 0; i< combinedInvoices.Count;i++)
            {
                 Date = "Order Date: " + combinedInvoices.ElementAt(i).Created.ToShortDateString();
                Total = " Order Total $" + combinedInvoices.ElementAt(i).Total * .01;
                Status = "Payment Status: " + combinedInvoices.ElementAt(i).Status.ToString();
                covm.CurrentOrderItems.Add(new Models.CurrentOrderItem(Date, Total, Status));           
            }
            //Set the ItemsSource with the ordered contacts
            listInvoice.ItemsSource = covm.CurrentOrderItems.ToList();
            listInvoice.ItemSelected += AddressList_ItemSelected;

        }

        private async void AddressList_ItemSelected(object sender, SelectedItemChangedEventArgs e) {


            var i = (listInvoice.ItemsSource as List<Models.CurrentOrderItem>).IndexOf(e.SelectedItem as CurrentOrderItem);

            await Navigation.PushModalAsync(new NavigationPage(new InvoiceDetails(combinedInvoices.ElementAt(i))));
    }

    private async void NavigateBook_OnClicked(object sender, EventArgs e)
        {
            //GlobalMethods.orderChangePopup();

            if (GlobalVar.loggedIn == true)
            {
                await Navigation.PushModalAsync((new Address()));
            }
            else
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Please login to place an order!", "OK");
            }
        }
    }
}