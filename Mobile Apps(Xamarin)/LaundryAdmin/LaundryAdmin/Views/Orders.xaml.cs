using LaundryAdmin.ViewModels;
using Stripe;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryAdmin.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Orders : ContentPage
    { 
        StripeList<Stripe.Invoice> orders;
        OrderViewModel ovm;
        public string Date { set; get; }
        public string Total { set; get; }
        public string Status { set; get; }

        public Orders()
        {
            InitializeComponent();


            ovm = new OrderViewModel();

            var options = new InvoiceListOptions
            {
            };
            var service = new InvoiceService();
            orders = service.List(
              options
            );

            for (int i = 0; i < orders.Data.Count; i++)
            {
                Date = "Order Date: " + orders.Data.ElementAt(i).Created.ToShortDateString();
                Total = " Order Total $" + orders.ElementAt(i).Total * .01;
                Status = "Payment Status: " + orders.Data.ElementAt(i).Status.ToString();
                ovm.OrderItems.Add(new Models.OrderItem(Date, Total, Status));
            }
            orderList.ItemsSource = ovm.OrderItems.ToList();
            orderList.ItemSelected += OrderList_ItemSelected;
        }

        private async void OrderList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            var i = (orderList.ItemsSource as List<Models.OrderItem>).IndexOf(e.SelectedItem as Models.OrderItem);

            await Navigation.PushModalAsync(new NavigationPage(new InvoiceDetails(orders.ElementAt(i))));
        }
    }
}