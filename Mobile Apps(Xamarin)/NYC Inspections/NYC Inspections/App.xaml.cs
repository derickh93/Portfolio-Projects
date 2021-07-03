using NYC_Inspections.Views;
using Xamarin.Forms;

namespace NYC_Inspections
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();
            MainPage = new SplashScreen();
        }

        protected override void OnStart()
        {
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }
    }
}