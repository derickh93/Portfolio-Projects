using System;
using System.Collections.Generic;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace NYC_Inspections.Popups
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class InfoPopup : Rg.Plugins.Popup.Pages.PopupPage
    {
        public InfoPopup(Dictionary<string, object> info)
        {
            InitializeComponent();
            var layout = stk;

            foreach (var keyValue in info)
            {
                Color temp = new Color();
                if (layout.Children.Count % 2 == 0)
                {
                    temp = Color.Orange;
                }
                else
                {
                    temp = Color.Navy;
                }

                var label = new Label { Text = keyValue.Key.ToString() + ": " + keyValue.Value.ToString(), TextColor = temp, FontSize = 12 };
                layout.Children.Add(label);
            }
            this.Content = layout;
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
        }

        private void Button_Clicked_Reset(object sender, EventArgs e)
        {
        }
    }
}