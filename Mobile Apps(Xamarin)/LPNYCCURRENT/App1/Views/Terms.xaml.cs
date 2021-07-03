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
    public partial class Terms : ContentPage
    {
        const string fileName = "terms.txt";
        public string Text { set; get; }
        public string TextColor { set; get; }
        public string Price { set; get; }
        public string PriceColor { set; get; }
        public FontAttributes FontAt { set; get; }


        PriceViewModel pvm;

        public Terms()
        {
            InitializeComponent();
            pvm = new PriceViewModel();
            NavigationPage.SetHasNavigationBar(this, false);
            LoadData();
        }

        private async void LoadData()
        {
            using (var stream = await FileSystem.OpenAppPackageFileAsync(fileName))
            {
                TextColor = "Black";
                PriceColor = "Green";
                termsPrice.ItemTemplate = new DataTemplate(typeof(CustomPriceCell));

                using (var reader = new StreamReader(stream))
                {
                    var line = reader.ReadLine();
                    while (line != null)
                    {
                        var array = line.Split('#');
                        Text = array[0].ToString();
                        TextColor = "Black";
                        PriceColor = "Green";
                        if (Text[0] == '@')
                        {
                            FontAt = FontAttributes.Bold;
                            Text = Text.ToUpper();
                            Text = Text.Substring(1);
                        }
                        else
                            FontAt = FontAttributes.None;
                        Price = "";
                        pvm.PriceItem.Add(new Models.PriceItem(Text, TextColor, Price, PriceColor, FontAt));
                        line = reader.ReadLine();
                    }
                }
                stream.Close();
            }
            termsPrice.ItemsSource = pvm.PriceItem.ToList();
            termsPrice.ItemSelected += AddressList_ItemSelected;
        }

        public class CustomPriceCell : ViewCell
        {
            public CustomPriceCell()
            {
                //instantiate each of our views
                var nameLabel = new Label() { FontSize = 14, TextColor = Color.Navy };
                var typeLabel = new Label() { FontSize = 12, TextColor = Color.Green };
                var verticaLayout = new StackLayout();
                var horizontalLayout = new StackLayout() { BackgroundColor = Color.Transparent };

                //set bindings
                nameLabel.SetBinding(Label.TextProperty, new Binding("Text"));
                typeLabel.SetBinding(Label.TextProperty, new Binding("Price"));
                nameLabel.SetBinding(Label.FontAttributesProperty, new Binding("FontAt"));


                //Set properties for desired design
                horizontalLayout.Orientation = StackOrientation.Horizontal;
                horizontalLayout.HorizontalOptions = LayoutOptions.Fill;
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
        protected override bool OnBackButtonPressed()
        {
            // true or false to disable or enable the action
            Application.Current.MainPage.Navigation.PopAsync(); //Remove the page currently on top.
            return false;
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