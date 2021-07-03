using LaundryPickupNYC.Services;
using LaundryPickupNYC.ViewModels;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class FAQ : ContentPage
    {
        public string Text { set; get; }
        public string TextColor { set; get; }
        public string Price { set; get; }
        public string PriceColor { set; get; }

        public FontAttributes FontAt { set; get; }


        PriceViewModel pvm;
        const string fileName = "FAQ.txt";

        public FAQ()
        {
            InitializeComponent();
            pvm = new PriceViewModel();
            LoadData();
        }

        private async void LoadData()
        {
            using (var stream = await FileSystem.OpenAppPackageFileAsync(fileName))
            {
                TextColor = "Black";
                PriceColor = "Green";
                faqList.ItemTemplate = new DataTemplate(typeof(CustomPriceCell));

                using (var reader = new StreamReader(stream))
                {
                    var line = reader.ReadLine();
                    while (line != null)
                    {
                        var array = line.Split('#');
                        Text = array[0].ToString();
                        FontAt = FontAttributes.Bold;
                        TextColor = "Black";
                        PriceColor = "Green";
                        Price = array[1].ToString();
                        pvm.PriceItem.Add(new Models.PriceItem(Text, TextColor, Price, PriceColor, FontAt));
                        line = reader.ReadLine();
                    }
                }
                stream.Close();
            }
            faqList.ItemsSource = pvm.PriceItem.ToList();
            faqList.ItemSelected += AddressList_ItemSelected;
        }

        public class CustomPriceCell : ViewCell
        {
            public CustomPriceCell()
            {
                //instantiate each of our views
                var nameLabel = new Label() { FontSize = 14, TextColor = Color.Navy};
                var typeLabel = new Label() { FontSize = 12, TextColor = Color.FromHex("#dfbd5c") };
                var verticaLayout = new StackLayout();
                var horizontalLayout = new StackLayout() { BackgroundColor = Color.Transparent };

                //set bindings
                nameLabel.SetBinding(Label.TextProperty, new Binding("Text"));
                typeLabel.SetBinding(Label.TextProperty, new Binding("Price"));
                nameLabel.SetBinding(Label.FontAttributesProperty, new Binding("FontAt"));

                //Set properties for desired design
                horizontalLayout.Orientation = StackOrientation.Horizontal;
                horizontalLayout.HorizontalOptions = LayoutOptions.FillAndExpand;
                //nameLabel.FontSize = 24;

                //add views to the view hierarchy
                verticaLayout.Children.Add(nameLabel);
                verticaLayout.Children.Add(typeLabel);
                horizontalLayout.Children.Add(verticaLayout);
                // add to parent view
                View = horizontalLayout;
            }
        }

        private async void AddressList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
        }
        private async void NavigateBook_OnClicked(object sender, EventArgs e)
        {
            GlobalMethods.orderChangePopup();

            //if (GlobalVar.loggedIn == true)
            //{
            //    await Navigation.PushModalAsync(new NavigationPage(new Address()));
            //}
            //else
            //{
            //    await App.Current.MainPage.DisplayAlert("Alert", "Please login to place an order!", "OK");
            //    await Shell.Current.GoToAsync("//home");


            //}
        }
    }
}