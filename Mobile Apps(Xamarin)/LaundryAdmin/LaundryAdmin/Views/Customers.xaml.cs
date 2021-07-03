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
    public partial class Customers : ContentPage
    {
        StripeList<Customer> customers;
        CustomerViewModel cvm;
        public Customers()
        {
            InitializeComponent();
            cvm = new CustomerViewModel();
            customerList.ItemsSource = cvm.CustItems;

            var options = new CustomerListOptions
            {
            };
            var service = new CustomerService();
            customers = service.List(
              options
            );
            for (int i = 0; i < customers.Data.Count; i++)
            {
                cvm.CustItems.Add(new Models.CustomerItem(customers.Data.ElementAt(i).Name, customers.Data.ElementAt(i).Phone, customers.Data.ElementAt(i).Email, customers.Data.ElementAt(i).Balance));
            }

            customerList.ItemsSource = cvm.CustItems;
        }
    }
}