using NYC_Inspections.Models.NYC_Inspections.Models;
using NYC_Inspections.Popups;
using NYC_Inspections.ViewModels;
using Rg.Plugins.Popup.Services;
using SODA;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace NYC_Inspections.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class InspectionList : ContentPage
    {
        private IEnumerable<Dictionary<string, object>> query;
        private SODA.Resource<Dictionary<string, object>> dataset;

        private InspectionListViewModel iivm;

        public string Dba { set; get; }
        public DateTime InspectionDate { set; get; }
        public string InspectionType { set; get; }
        public string strAddress { get; set; }

        private bool isBusy = true;

        public InspectionList(Dictionary<string, object> info, SODA.Resource<Dictionary<string, object>> ds)
        {
            InitializeComponent();
            try
            {
                loadingSpinner.IsRunning = true;
                loadingSpinner.IsVisible = true;
                loadingSpinner.IsEnabled = true;

                loadingSpinner.Color = Color.Orange;

                iivm = new InspectionListViewModel();

                dataset = ds;

                object camis = "";
                info.TryGetValue("camis", out camis);
                permitNumber.Text = camis.ToString();

                CallingMethod(camis.ToString());

                object grade = "";
                if (info.TryGetValue("grade", out grade))
                {
                    loadGradeImage(grade.ToString());
                }
                else
                {
                    loadGradeImage("N");
                }

                object dba = "";
                info.TryGetValue("dba", out dba);
                name.Text = dba.ToString();

                object building = "";
                object boro = "";
                object street = "";
                object zip = "";

                if (info.TryGetValue("building", out building))
                {
                    Address.Text = building.ToString();
                }
                else
                {
                    building = "";
                    Address.Text = building.ToString();
                }

                if (info.TryGetValue("street", out street))
                {
                    Address.Text = Address.Text.ToString() + " " + street.ToString();
                }
                else
                {
                    street = "";
                    Address.Text = Address.Text.ToString() + " " + street.ToString();
                }

                if (info.TryGetValue("boro", out boro))
                {
                    Boro.Text = boro.ToString() + ", NY";
                }
                else
                {
                    Boro.Text = "NY";
                }

                if (info.TryGetValue("zipcode", out zip))
                {
                    Zip.Text = zip.ToString();
                }
                else
                {
                    Zip.Text = "";
                }

                object fooType = "";
                info.TryGetValue("cuisine_description", out fooType);
                foodType.Text = fooType.ToString();
            }
            catch (Exception ex)
            {
                DisplayAlert("test", ex.ToString(), "cancel");
            }
        }

        private async void RestaurantList_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            var i = (listRestaurants.ItemsSource as List<InspectionItem>).IndexOf(e.SelectedItem as InspectionItem);
            if (e.SelectedItem == null)
            {
                return;
            }
((ListView)sender).SelectedItem = null;
            await PopupNavigation.Instance.PushAsync(new InfoPopup(query.ElementAt(i)), true);
        }

        public async Task GetInspections(string searchText)
        {
            await Task.Run(() =>
            {
                // some long running task
                try
                {
                    iivm = new InspectionListViewModel();
                    var soql = new SoqlQuery().FullTextSearch(searchText).Limit(5);
                    query = dataset.Query<Dictionary<string, object>>(soql);

                    for (int i = 0; i < query.Count(); i++)
                    {
                        object inspectionDate = "";
                        object inspectionType = "";
                        object score = "";

                        if (query.ElementAt(i).TryGetValue("inspection_date", out inspectionDate))
                        {
                            InspectionDate = (DateTime)inspectionDate;
                        }
                        else
                        {
                            InspectionDate = new DateTime();
                        }

                        if (query.ElementAt(i).TryGetValue("inspection_type", out inspectionType))
                        {
                            InspectionType = inspectionType.ToString();
                        }
                        else
                        {
                            InspectionType = "N/A";
                        }

                        InspectionItem temp = new InspectionItem(InspectionDate, InspectionType);

                        iivm.InspectionItems.Add(temp);
                    }

                    iivm.InspectionItems.Sort((x, y) => DateTime.Compare(x.InspectionDate, y.InspectionDate));
                    iivm.InspectionItems.Reverse();
                    //await PopupNavigation.Instance.PopAsync();
                }
                catch (Exception ex)
                {
                    DisplayAlert("break point", ex.ToString(), "cancel");
                }
            });
        }

        private async void CallingMethod(string camis)
        {
            await GetInspections(camis);

            listRestaurants.ItemsSource = iivm.InspectionItems.ToList();
            listRestaurants.ItemSelected += RestaurantList_ItemSelected;

            loadingSpinner.IsRunning = false;
            loadingSpinner.IsVisible = false;
            loadingSpinner.IsEnabled = false;
        }

        private void loadGradeImage(string grade)
        {
            //Grade = grade.ToString();
            if (grade.ToString().Equals("A"))
            {
                image.Source = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_A.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);

            }
            else if (grade.ToString().Equals("B"))
            {
                image.Source = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_B.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
            }
            else if (grade.ToString().Equals("C"))
            {
                image.Source = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_C.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
            }
            else if (grade.ToString().Equals("Z"))
            {
                image.Source = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_GP.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
            }
            else if (grade.ToString().Equals("N"))
            {
                image.Source = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_NG.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
            }
            else if (grade.ToString().Equals("P"))
            {
                image.Source = ImageSource.FromResource("NYC_Inspections.Images.NYCRestaurant_Closed.png", typeof(ImageResourceExtension).GetTypeInfo().Assembly);
            }
        }
    }
}