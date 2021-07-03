using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace Loaded.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SplashScreen : ContentPage
    {
        public bool IsPlaying { get; set; }

        public SplashScreen()
        {
            NavigationPage.SetHasNavigationBar(this, false);
            Init();
            InitializeComponent();
            animate_Clicked(null, null);
            BindingContext = this;
            Button_Clicked(null, null);
            StartTimer();
            async void StartTimer()
            {
                await Task.Delay(9000); //60 minutes 
                                        //start your activity here
                await Shell.Current.GoToAsync("//home");
                Navigation.PopModalAsync();
            }
        }

        async void animate_Clicked(object sender, EventArgs e)
        {
            logoimg.RelRotateTo(360, 4000);
        }

        void Button_Clicked(object sender, EventArgs e)
        {
            IsPlaying = !IsPlaying;
            OnPropertyChanged(nameof(IsPlaying));
        }

        public async void Init()
        {
            if (!GlobalVar.loading)
            {
                await Shell.Current.GoToAsync("//home");
            }
        }
    }
}