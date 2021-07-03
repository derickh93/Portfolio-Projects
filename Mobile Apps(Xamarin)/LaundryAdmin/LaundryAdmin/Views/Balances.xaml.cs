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
    public partial class Balances : ContentPage
    {
        public Balances()
        {
            InitializeComponent();

            var service = new BalanceService();
            Balance balance = service.Get();
            balText.Text = balance.Available.ElementAt(0).Amount.ToString();
            pendText.Text = balance.Pending.ElementAt(0).Amount.ToString();

            //    var options = new BalanceTransactionListOptions
            //    {
            //        Limit = 3,
            //    };
            //var service = new BalanceTransactionService();
            //StripeList<BalanceTransaction> balanceTransactions = service.List(
            //  options
            //);
        }
    }
}