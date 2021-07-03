using NYC_Inspections.Services;
using System;
using System.Linq;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace NYC_Inspections.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SplashScreen : ContentPage
    {
        public bool IsPlaying { get; set; }

        public SplashScreen()
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);
            //animate_Clicked(null, null);
            StartTimer();
            async void StartTimer()
            {
                GetZip();
                await Task.Delay(5000);//60 minutes
                                       //start your activity here
                                       //await Shell.Current.GoToAsync("//home");
                App.Current.MainPage = new AppShell();
            }
        }

        private async void animate_Clicked(object sender, EventArgs e)
        {
            logoimg.Opacity = 0;
            await logoimg.FadeTo(1, 3000);
        }

        private async Task GetZip()
        {
            try
            {
                var location = await Geolocation.GetLastKnownLocationAsync();

                var lat = location.Latitude;
                var lon = location.Longitude;


                GlobalVar.latitude = location.Latitude;
                GlobalVar.longitude = location.Longitude;

                var placemarks = await Geocoding.GetPlacemarksAsync(lat, lon);
                GlobalVar.currentZip = placemarks.First().PostalCode;
            }
            catch (FeatureNotSupportedException fnsEx)
            {
                await DisplayAlert("Error", fnsEx.ToString(), "cancel");
            }
            catch (FeatureNotEnabledException fneEx)
            {
                await DisplayAlert("Error", fneEx.ToString(), "cancel");
            }
            catch (PermissionException pEx)
            {
                await DisplayAlert("Error", pEx.ToString(), "cancel");
            }
            catch (Exception ex)
            {
                await DisplayAlert("Error", ex.ToString(), "cancel");
            }
        }
    }
}