using Newtonsoft.Json;
using Rg.Plugins.Popup.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class DryCleanPopup
    {

        public DryCleanPopup()
        {
            InitializeComponent();
        }

        private async void Button_OnClicked(object sender, EventArgs e)
        {
            Object_Classes.Transaction.orderQuantity = bagsQuantity.Text;
            await PopupNavigation.PopAsync();
        }
    }
}