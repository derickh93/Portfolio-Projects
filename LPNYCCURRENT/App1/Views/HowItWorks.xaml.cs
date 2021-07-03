using LaundryPickupNYC.Services;
using Syncfusion.SfCarousel.XForms;
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
    public partial class HowItWorks : ContentPage
    {
        public HowItWorks()
        {
            InitializeComponent();
            SfCarousel carousel = new SfCarousel()
            {
                ItemWidth = 300,
                ItemHeight = 450
            };
            ObservableCollection<SfCarouselItem> carouselItems = new ObservableCollection<SfCarouselItem>();
            carouselItems.Add(new SfCarouselItem() { ImageName = "howone.jpg"});
            carouselItems.Add(new SfCarouselItem() { ImageName = "howtwo.jpg" });
            carouselItems.Add(new SfCarouselItem() { ImageName = "howthree.jpg" });

            carousel.ItemsSource = carouselItems;

            this.Content = carousel;
        }

        private async void NavigateBook_OnClicked(object sender, EventArgs e)
        {
            //GlobalMethods.orderChangePopup();

            if (GlobalVar.loggedIn == true)
            {
                await Navigation.PushModalAsync(new NavigationPage(new Address()));
            }
            else
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Please login to place an order!", "OK");
                await Shell.Current.GoToAsync("//home");
            }
        }
    }
}