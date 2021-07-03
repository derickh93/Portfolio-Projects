using Rg.Plugins.Popup.Services;
using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;


namespace LaundryPickupNYC.Services
{
    public static class GlobalMethods
    {
        public static void orderChangePopup()
        {
            PopupNavigation.PushAsync(new ContactPopup());
        }
    }
}
