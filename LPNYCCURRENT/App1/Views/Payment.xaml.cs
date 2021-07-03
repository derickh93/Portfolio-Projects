using Rg.Plugins.Popup.Services;
using Stripe;
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
    public partial class Payment : ContentPage
    {
        StripeList<PaymentMethod> paymentMethods;
        List<string> payList;
        public Payment()
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);


            Object_Classes.CollectionHelper.creditCollection = new ObservableCollection<string>();
            try
            {
                paymentMethods = StripeCheckout.loadCards();
            }
            catch (Exception ex)
            {
                App.Current.MainPage.DisplayAlert("Alert", ex.Message.ToString(), "Ok");
            }


            if (paymentMethods.Data.Count == 0)
            {
                App.Current.MainPage.DisplayAlert("Alert", "No payment method on file, please add a card!", "OK");
            }
            else
            {
                payList = new List<string>();
                for (int i = 0; i < paymentMethods.Data.Count; i++)
                {
                    Object_Classes.CollectionHelper.creditCollection.Add(paymentMethods.Data[i].Card.Last4);
                }
                //App.Current.MainPage.DisplayAlert("Alert", paymentMethods.ToString(), "OK");
                cardList.ItemsSource = Object_Classes.CollectionHelper.creditCollection;
                cardList.ItemSelected += CardList_ItemSelected;
            }
        }

        private async void CardList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            var i = (cardList.ItemsSource as ObservableCollection<string>).IndexOf(e.SelectedItem as string);
            bool answer = await DisplayAlert("Question?", "Would you like to use this card?", "Yes", "No");
            if (answer)
            {

                //cardList.ItemsSource = Object_Classes.CollectionHelper.creditCollection;

                Navigation.PushModalAsync(new NavigationPage(new ReviewPage(paymentMethods.Data.ElementAt(i))));

            }
            else
            {
                //do nothing
            }
        }
        private void Button_Clicked(object sender, EventArgs e)
        {
            PopupNavigation.PushAsync(new SaveCard());
        }
    }
}