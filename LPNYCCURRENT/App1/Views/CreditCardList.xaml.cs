using Rg.Plugins.Popup.Services;
using Stripe;
using Stripe.BillingPortal;
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
    public partial class CreditCardList : ContentPage
    {
        StripeList<PaymentMethod> paymentMethods;
        public CreditCardList()
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);

            try
            {
                paymentMethods = StripeCheckout.loadCards();
            }
            catch (Exception ex)
            {
                App.Current.MainPage.DisplayAlert("Alert", ex.Message.ToString(), "Ok");
            }

            Object_Classes.CollectionHelper.creditCollection = new ObservableCollection<string>();


            for (int i = 0; i < paymentMethods.Data.Count; i++)
            {
                Object_Classes.CollectionHelper.creditCollection.Add(paymentMethods.Data[i].Card.Last4);
            }
            cardList.ItemsSource = Object_Classes.CollectionHelper.creditCollection;
            cardList.ItemSelected += CardList_ItemSelected;
        }

        private async void CardList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            var i = (cardList.ItemsSource as ObservableCollection<string>).IndexOf(e.SelectedItem as string);
            bool answer = await DisplayAlert("Question?", "Would you like to delete this card?", "Yes", "No");
            if (answer)
            {
               StripeCheckout.deleteCard(paymentMethods.Data[i].Id);
                Object_Classes.CollectionHelper.creditCollection.RemoveAt(i);
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