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
    public partial class Products : ContentPage
    {
        StripeList<Product> products;
            ProductViewModel pvm;
            public Products()
            {
                InitializeComponent();


                pvm = new ProductViewModel();

                var options = new ProductListOptions
                {
                    Limit = 3,
                };
                var service = new ProductService();
                products = service.List(
                  options
                );

                for (int i = 0; i < products.Data.Count; i++)
                {
                    pvm.ProductItems.Add(new Models.ProductItem(products.Data.ElementAt(i).Name, products.Data.ElementAt(i).Description));
                }

                productList.ItemsSource = pvm.ProductItems;

            }
        }
}