using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Xamarin.Forms.Xaml;

namespace NYC_Inspections.Popups
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class FilterPopup : Rg.Plugins.Popup.Pages.PopupPage
    {
        private string cuisineFilter, gradeFilter, boroFilter, sortFilter;
        private IEnumerable<Dictionary<string, object>> query;
        private SODA.Resource<Dictionary<string, object>> tempDataSet;

        public FilterPopup(SODA.Resource<Dictionary<string, object>> dataset)
        {
            InitializeComponent();
            tempDataSet = dataset;
            cuisinePicker.Items.Add("Pizza");
            cuisinePicker.Items.Add("Chinese");
            cuisinePicker.Items.Add("Hamburger");
            cuisinePicker.Items.Add("American");
            cuisinePicker.Items.Add("Carribean");

            gradePicker.Items.Add("A");
            gradePicker.Items.Add("B");
            gradePicker.Items.Add("C");
            gradePicker.Items.Add("N");
            gradePicker.Items.Add("Z");
            gradePicker.Items.Add("P");

            boroPicker.Items.Add("Queens");
            boroPicker.Items.Add("Brooklyn");
            boroPicker.Items.Add("The Bronx");
            boroPicker.Items.Add("Manhattan");
            boroPicker.Items.Add("Staten Island");

            sortPicker.Items.Add("Name");
            sortPicker.Items.Add("Inspection Date");
            sortPicker.Items.Add("Cuisine");
            sortPicker.Items.Add("Grade");
            sortPicker.Items.Add("Distance");

            cuisinePicker.SelectedIndexChanged += (sender, args) =>
            {
                if (cuisinePicker.SelectedIndex == -1)
                {
                    cuisineFilter = "";
                }
                else
                {
                    cuisineFilter = cuisinePicker.Items[cuisinePicker.SelectedIndex];
                }
            };

            sortPicker.SelectedIndexChanged += (sender, args) =>
            {
                if (sortPicker.SelectedIndex == -1)
                {
                    sortFilter = "";
                }
                else if (sortPicker.SelectedIndex == 0)
                {
                    //rivm.RestaurantItems.Sort((x, y) => DateTime.Compare(x.GradeDate, y.GradeDate));
                }
                else
                {
                    sortFilter = sortPicker.Items[sortPicker.SelectedIndex];
                }
            };

            gradePicker.SelectedIndexChanged += (sender, args) =>
            {
                if (gradePicker.SelectedIndex == -1)
                {
                    gradeFilter = "";
                }
                else
                {
                    gradeFilter = gradePicker.Items[gradePicker.SelectedIndex];
                }
            };

            boroPicker.SelectedIndexChanged += (sender, args) =>
            {
                if (boroPicker.SelectedIndex == -1)
                {
                    boroFilter = "";
                }
                else
                {
                    boroFilter = boroPicker.Items[boroPicker.SelectedIndex];
                }
            };
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();
        }

        protected override void OnDisappearing()
        {
            base.OnDisappearing();
        }

        // ### Methods for supporting animations in your popup page ###

        // Invoked before an animation appearing
        protected override void OnAppearingAnimationBegin()
        {
            base.OnAppearingAnimationBegin();
        }

        // Invoked after an animation appearing
        protected override void OnAppearingAnimationEnd()
        {
            base.OnAppearingAnimationEnd();
        }

        // Invoked before an animation disappearing
        protected override void OnDisappearingAnimationBegin()
        {
            base.OnDisappearingAnimationBegin();
        }

        // Invoked after an animation disappearing
        protected override void OnDisappearingAnimationEnd()
        {
            base.OnDisappearingAnimationEnd();
        }

        protected override Task OnAppearingAnimationBeginAsync()
        {
            return base.OnAppearingAnimationBeginAsync();
        }

        protected override Task OnAppearingAnimationEndAsync()
        {
            return base.OnAppearingAnimationEndAsync();
        }

        protected override Task OnDisappearingAnimationBeginAsync()
        {
            return base.OnDisappearingAnimationBeginAsync();
        }

        protected override Task OnDisappearingAnimationEndAsync()
        {
            return base.OnDisappearingAnimationEndAsync();
        }

        // ### Overrided methods which can prevent closing a popup page ###

        // Invoked when a hardware back button is pressed
        protected override bool OnBackButtonPressed()
        {
            // Return true if you don't want to close this popup page when a back button is pressed
            return base.OnBackButtonPressed();
        }

        // Invoked when background is clicked
        protected override bool OnBackgroundClicked()
        {
            // Return false if you don't want to close this popup page when a background of the popup page is clicked
            return base.OnBackgroundClicked();
        }

        private void Button_Clicked_Filter(object sender, EventArgs e)
        {
            DisplayAlert("test", cuisineFilter + " " + gradeFilter + " " + sortFilter + " " + boroFilter, "ok");
        }

        private void Button_Clicked_Reset(object sender, EventArgs e)
        {
        }
    }
}