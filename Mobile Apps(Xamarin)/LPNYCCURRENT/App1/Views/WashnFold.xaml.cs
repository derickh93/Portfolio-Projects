using LaundryPickupNYC.Services;
using LaundryPickupNYC.ViewModels;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class WashnFold : ContentPage
    {
        public string Text { set; get; }
        public string TextColor { set; get; }
        public string Price { set; get; }
        public string PriceColor { set; get; }
        public FontAttributes FontAt { set; get; }

        PriceViewModel pvm;

        string fileName;


        public WashnFold(string classId)
        {
            InitializeComponent();
            pvm = new PriceViewModel();
            NavigationPage.SetHasNavigationBar(this, false);
            LoadData(classId);
        }

        private async void LoadData(string classId)
        {
            switch (classId)
            {
                case "wash":
                    pageLabel.Text = "Price: Wash & Fold";
                    fileName = "priceList.txt";
                    break;
                case "dry":
                    pageLabel.Text = "Price: Dry Clean";
                    fileName = "dryCleanList.txt";
                    break;
                case "iron":
                    pageLabel.Text = "Price: Wash & Iron";
                    fileName = "washAndIronPrice.txt";
                    break;
                case "home":
                    pageLabel.Text = "Price: Home & Bedding";
                    fileName = "homeBedPrice.txt";
                    break;
                default:
                    Console.WriteLine("Default case");
                    break;
            }

            using (var stream = await FileSystem.OpenAppPackageFileAsync(fileName))
            {
                TextColor = "Black";
                PriceColor = "Green";
                listPrice.ItemTemplate = new DataTemplate(typeof(CustomPriceCell));

                using (var reader = new StreamReader(stream))
                {
                    var line = reader.ReadLine();
                    while (line != null){
                        var array = line.Split('$');
                        if (array.Count() == 1)
                        {
                            Text = array[0].ToString();
                            Price = "";
                            FontAt = FontAttributes.Bold;
                            pvm.PriceItem.Add(new Models.PriceItem("", TextColor, "", PriceColor,FontAt));

                        }
                        else
                        {
                            Text = array[0].ToString();
                            Price = "$" +array[1].ToString();
                            FontAt = FontAttributes.None;
                        }
                        pvm.PriceItem.Add(new Models.PriceItem(Text, TextColor, Price, PriceColor,FontAt));
                        line = reader.ReadLine();
                    }
                }
                stream.Close();
            }
                    listPrice.ItemsSource = pvm.PriceItem.ToList();
                    listPrice.ItemSelected += AddressList_ItemSelected;
        }

        public class CustomPriceCell : ViewCell
        {
            public CustomPriceCell()
            {
                //instantiate each of our views
                var nameLabel = new Label() { FontSize = 14, TextColor = Color.Navy};
                var typeLabel = new Label() { FontSize = 12, TextColor = Color.Green };
                var verticaLayout = new StackLayout();
                var horizontalLayout = new StackLayout() { BackgroundColor = Color.Transparent};

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


        private async void NavigateButton_OnClicked(object sender, EventArgs e)
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