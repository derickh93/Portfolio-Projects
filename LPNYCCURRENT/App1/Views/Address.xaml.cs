using Rg.Plugins.Popup.Services;
using Syncfusion.SfSchedule.XForms;
using Syncfusion.XForms.PopupLayout;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Address : ContentPage
    {
        public Address()
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);
            Object_Classes.CollectionHelper.addressCollection = new ObservableCollection<Object_Classes.Address>();


            for (int i = 0; i < GlobalVar.user.custAddress.Count; i++)
            {
                Object_Classes.CollectionHelper.addressCollection.Add(GlobalVar.user.custAddress.ElementAt(i));
            }

            addressList.ItemSelected += AddressList_ItemSelected;
            addressList.ItemsSource = Object_Classes.CollectionHelper.addressCollection;
        }

        private async void AddressList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {
        var i = (addressList.ItemsSource as ObservableCollection<Object_Classes.Address>).IndexOf(e.SelectedItem as Object_Classes.Address);
        bool answer = await DisplayAlert("Pickup & Delivery Address", "Laundry will be picked up and deliverd at: " + Object_Classes.CollectionHelper.addressCollection.ElementAt(i), "Continue", "Cancel");
        if (answer)
        {
                Object_Classes.Transaction.address = Object_Classes.CollectionHelper.addressCollection.ElementAt(i);
                await Navigation.PushModalAsync(new NavigationPage(new Preference()));

            }
            else
        {
            //do nothing
        }
    }

    private async void NavigateButton_OnClicked(object sender, EventArgs e)
        {
            await Navigation.PushModalAsync(new NavigationPage(new Preference()));
        }

        private void ClickToShowPopup_Clicked(object sender, EventArgs e)
        {
                PopupNavigation.PushAsync(new PopupNewTaskView());
        }
    }
}