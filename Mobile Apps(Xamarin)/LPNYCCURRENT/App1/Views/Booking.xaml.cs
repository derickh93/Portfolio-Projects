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
    public partial class Booking : TabbedPage
    {
        public Booking()
        {
            InitializeComponent();
            this.Children.Add(new CurrentOrders() { Title = "Current Orders" });
            this.Children.Add(new PastOrders() { Title = "Past Orders"});
            this.BarBackgroundColor = Color.FromHex("#1c2f74");
            this.BarTextColor = Color.FromHex("#dfbd5c");
        }
    }
}