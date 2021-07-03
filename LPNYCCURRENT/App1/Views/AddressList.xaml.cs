using Newtonsoft.Json;
using Rg.Plugins.Popup.Services;
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
    public partial class AddressList : ContentPage
    {
        FirebaseHelper fh = new FirebaseHelper();
        public AddressList()
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);
            //App.Current.MainPage.DisplayAlert("Alert", "JSON Object: " + GlobalVar.user.custAddress, "OK");

            Object_Classes.CollectionHelper.addressCollection = new ObservableCollection<Object_Classes.Address>();

            for(int i = 0; i < GlobalVar.user.custAddress.Count;i++)
            {
                Object_Classes.CollectionHelper.addressCollection.Add(GlobalVar.user.custAddress.ElementAt(i));
            }
            addressList.ItemsSource = Object_Classes.CollectionHelper.addressCollection;
            addressList.ItemSelected += AddressList_ItemSelected;
        }

        private async void AddressList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
    {
        var i = (addressList.ItemsSource as ObservableCollection<Object_Classes.Address>).IndexOf(e.SelectedItem as Object_Classes.Address);
        bool answer = await DisplayAlert("Question?", "Would you like to delete this address?", "Yes", "No");
        if (answer)
        {
                Object_Classes.CollectionHelper.addressCollection.RemoveAt(i);
                GlobalVar.user.custAddress = Object_Classes.CollectionHelper.addressCollection.ToList();
                GlobalVar.user.jsonList = JsonConvert.SerializeObject(GlobalVar.user.custAddress);
                await fh.UpdatePerson(GlobalVar.user);

            }
        else
        {
            //do nothing
        }
    }

    private void OnPopupTask(object sender, EventArgs e)
        {
            PopupNavigation.PushAsync(new PopupNewTaskView());
        }
    }
}