using NYC_Inspections.Models;
using NYC_Inspections.Popups;
using NYC_Inspections.Services;
using NYC_Inspections.ViewModels;
using Rg.Plugins.Popup.Services;
using SODA;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Reflection;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace NYC_Inspections.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class List : ContentPage
    {
        private IEnumerable<Dictionary<string, object>> query;
        private SODA.Resource<Dictionary<string, object>> dataset;
        private RestaurantItemViewModel rivm;
        private int[] indexArr;
        public static Action EmulateBackPressed;
        int sortIndex;

        private bool AcceptBack;

        public string Dba { set; get; }
        public string Cuisine { set; get; }
        public ImageSource ImageUri { get; set; }
        public string Camis { get; set; }
        public string Grade { get; set; }
        public int Index { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public DateTime Inspection_Date { get; set; }

        public int limit = 20;
        public int offset = 0;
        public string sortFilter = "";
        public string GlobalSearch = "";
        public string locQuery = $"zipcode = '{GlobalVar.currentZip}'";

        public List()
        {
            InitializeComponent();

            sortPicker.Items.Add("Inspection Date");
            sortPicker.Items.Add("Name");
            sortPicker.Items.Add("Cuisine");
            sortPicker.Items.Add("Grade");
            sortPicker.Items.Add("Distance");
            sortPicker.Items.Add("<--REVERSE-->");


            sortPicker.SelectedIndexChanged += (sender, args) =>
            {
                if (sortPicker.SelectedIndex == -1)
                {
                }
                if (sortPicker.SelectedIndex == 0)
                {
                    sortFilter = sortPicker.Items[sortPicker.SelectedIndex];
                    rivm.RestaurantItems.Sort((x, y) => DateTime.Compare(x.Inspection_Date, y.Inspection_Date));
                }
                if (sortPicker.SelectedIndex == 1)
                {
                    sortFilter = sortPicker.Items[sortPicker.SelectedIndex];
                    rivm.RestaurantItems.Sort((x, y) => string.Compare(x.Dba, y.Dba));
                }
                if (sortPicker.SelectedIndex == 2)
                {
                    sortFilter = sortPicker.Items[sortPicker.SelectedIndex];
                    rivm.RestaurantItems.Sort((x, y) => string.Compare(x.Cuisine, y.Cuisine));
                }
                if (sortPicker.SelectedIndex == 3)
                {
                    sortFilter = sortPicker.Items[sortPicker.SelectedIndex];
                    rivm.RestaurantItems.Sort((x, y) => string.Compare(x.Grade, y.Grade));
                }
                if (sortPicker.SelectedIndex == 4)
                {
                    sortFilter = sortPicker.Items[sortPicker.SelectedIndex];
                    rivm.RestaurantItems = rivm.RestaurantItems.OrderBy(a => a.Distance).ToList();
                }
                if (sortPicker.SelectedIndex == 5)
                {
                    rivm.RestaurantItems.Reverse();
                }

                sortIndex = sortPicker.SelectedIndex; ;
                sortPicker.WidthRequest = 175;
                //sortPicker.Title = sortFilter;







                listRestaurants.ItemsSource = rivm.RestaurantItems.ToList();
                listRestaurants.ItemSelected += RestaurantList_ItemSelected;
            };

            rivm = new RestaurantItemViewModel();

            try
            {
                var client = new SodaClient("https://data.ny.gov", "4kAeJM4FC1linQef7ldqrutXB");

                dataset = client.GetResource<Dictionary<string, object>>("43nn-pn8j");

                //var soql2 = new SoqlQuery().Limit(20).Offset(0);
                //query = dataset.Query<Dictionary<string, object>>(soql2);

                //Object outObject = "";
                //query.FirstOrDefault().TryGetValue("dba",out outObject);
                //DisplayAlert("test",outObject.ToString(),"cancel");

            }
            catch (HttpRequestException)
            {
                DisplayAlert("Error", "Connection failure", "OK");
            }

            getRestaurants(GlobalSearch);
        }

        private async void RestaurantList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            var i = (listRestaurants.ItemsSource as List<RestaurantItem>).IndexOf(e.SelectedItem as RestaurantItem);
            if (e.SelectedItem == null)
            {
                return;
            }
            ((ListView)sender).SelectedItem = null;
            //await PopupNavigation.Instance.PushAsync(new LoadingPopup(), true);
            //await PopupNavigation.Instance.PushAsync(new InfoPopup(query.ElementAt(rivm.RestaurantItems.ElementAt(i).Index)), true);
            await Navigation.PushAsync(new InspectionList(query.ElementAt(rivm.RestaurantItems.ElementAt(i).Index), dataset));
        }

        private void Button_Clicked_Search(object sender, EventArgs e)
        {
            limit = 20;
            offset = 0;
            PopupNavigation.Instance.PushAsync(new LoadingPopup(), true);
            rivm = new RestaurantItemViewModel();
            GlobalSearch = entry.Text.ToString();
            try
            {
                getRestaurants(GlobalSearch);
            }
            catch
            {
                DisplayAlert("Error", "Please enter valid search parameters", "Close");
            }
 
        }

        private void Button_Clicked_Filter(object sender, EventArgs e)
        {
            sortPicker.Focus();
            //PopupNavigation.Instance.PushAsync(new FilterPopup(dataset), true);
        }

        private void Button_Clicked_Reset(object sender, EventArgs e)
        {
            rivm = new RestaurantItemViewModel();
            limit = 20;
            offset = 0;
            GlobalSearch = "";
            getRestaurants(GlobalSearch);

        }

        private async void Button_Clicked_Map(object sender, EventArgs e)
        {
            indexArr = rivm.RestaurantItems.Select(i => i.Index).ToArray();
            await Navigation.PushAsync(new Map(query, indexArr,dataset));
        }

        private async void getRestaurants(string searchText)
        {
            SoqlQuery soql;
            try
            {
                soql = new SoqlQuery().FullTextSearch(searchText).Limit(limit).Offset(offset).Where(locQuery);
            }
            catch (Exception ex)
            {
                soql = new SoqlQuery().FullTextSearch(searchText).Limit(limit).Offset(offset);
            }
            try
            {
            query = dataset.Query<Dictionary<string, object>>(soql);
            if(query.Count() == 0)
            {
                var action = await DisplayAlert("No Results","Would you like to search the entire database?","yes","no");
                if (action)
                {
                    var soql2 = new SoqlQuery().FullTextSearch(searchText).Limit(limit).Offset(offset);
                    query = dataset.Query<Dictionary<string, object>>(soql2);
                }
            }            }
            catch (Exception ex)
            {
                DisplayAlert("error", ex.ToString(), "cancel");
            }






            try
            {

                for (int i = 0; i < query.Count(); i++)
                {
                    object keyValue = "";
                    object grade = "";
                    object cuisine = "";
                    object inspection_date = "";

                    object latitude = 0;
                    object longitude = 0;
                    Index = i;

                    if (query.ElementAt(i).TryGetValue("dba", out keyValue))
                    {
                        Dba = keyValue.ToString();
                    }
                    else
                    {
                        Dba = "N/A";
                    }

                    object camis = "";
                    query.ElementAt(i).TryGetValue("camis", out camis);
                    Camis = camis.ToString();




                    if (query.ElementAt(i).TryGetValue("latitude", out latitude))
                    {
                        Latitude = Double.Parse(latitude.ToString());
                    }
                    else
                    {
                        latitude = "0";
                        Latitude = Double.Parse(latitude.ToString());

                    }

                    if (query.ElementAt(i).TryGetValue("longitude", out longitude))
                    {
                        Longitude = Double.Parse(longitude.ToString());

                    }
                    else
                    {
                        longitude = "0";
                        Longitude = Double.Parse(longitude.ToString());

                    }


                    if (query.ElementAt(i).TryGetValue("inspection_date", out inspection_date))
                    {
                        Inspection_Date = (DateTime)inspection_date;
                    }
                    else
                    {
                        Inspection_Date = new DateTime();
                    }



                    if (query.ElementAt(i).TryGetValue("cuisine_description", out cuisine))
                    {
                        Cuisine = cuisine.ToString();
                    }
                    else
                    {
                    }

                    if (query.ElementAt(i).TryGetValue("grade", out grade))
                    {
                        //Grade = grade.ToString();
                        if (grade.ToString().Equals("A"))
                        {
                            Grade = "A";

                            ImageUri = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_A.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
                            
                        }
                        else if (grade.ToString().Equals("B"))
                        {
                            Grade = "B";

                            ImageUri = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_B.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
                        }
                        else if (grade.ToString().Equals("C"))
                        {
                            Grade = "C";

                            ImageUri = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_C.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
                        }
                        else if (grade.ToString().Equals("Z"))
                        {
                            Grade = "Z";

                            ImageUri = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_GP.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
                        }
                        else if (grade.ToString().Equals("N"))
                        {
                            Grade = "N";

                            ImageUri = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_NG.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
                        }
                        else if (grade.ToString().Equals("P"))
                        {
                            Grade = "P";

                            ImageUri = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_Closed.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
                        }
                    }
                    else
                    {
                        Grade = "";
                        grade = "";
                    }
                    //if (grade.Equals(""))
                    //{
                      //  DisplayAlert("test",Grade,"cancel");
                    //}
                    //else
                    
                    
                        Models.RestaurantItem temp = new Models.RestaurantItem(Dba, Cuisine, ImageUri, Camis, Grade, Index, Latitude, Longitude, Inspection_Date);

                        rivm.RestaurantItems.Add(temp);                    
                }
            }
            catch (Exception ex)
            {
                DisplayAlert("error",ex.ToString(),"cancel");
            }
            rivm.RestaurantItems.Sort((x, y) => string.Compare(x.Dba, y.Dba));


            listRestaurants.ItemsSource = rivm.RestaurantItems.ToList();
            listRestaurants.ItemSelected += RestaurantList_ItemSelected;
            if (PopupNavigation.Instance.PopupStack.Count > 0)
            {
                PopupNavigation.Instance.PopAsync();
            }
            limit += 20;
            offset += 20;
        }

        private async Task GetZip()
        {
            try
            {
                var location = await Geolocation.GetLastKnownLocationAsync();

                var lat = location.Latitude;
                var lon = location.Longitude;

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

        private void Button_Clicked_Load(object sender, EventArgs e)
        {
            try
            {
                getRestaurants(GlobalSearch);
            }
            catch (Exception ex)
            {
                DisplayAlert("error",ex.ToString(),"cancel");
            }
        }

        protected override bool OnBackButtonPressed()
        {
            if (AcceptBack)
                return false;

            PromptForExit();
            return true;
        }

        private async void PromptForExit()
        {
            if (await DisplayAlert("", "Are you sure to exit?", "Yes", "No"))
            {
                AcceptBack = true;
                System.Diagnostics.Process.GetCurrentProcess().Kill();
            }
        }
    }
}