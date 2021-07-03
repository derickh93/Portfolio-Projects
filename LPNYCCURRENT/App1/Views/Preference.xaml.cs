using Rg.Plugins.Popup.Services;
using Syncfusion.SfSchedule.XForms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Preference : ContentPage
    {
        public Preference()
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);
        }

        private async void NavigateButton_OnClicked(object sender, EventArgs e)
        {
            Syncfusion.Licensing.SyncfusionLicenseProvider.RegisterLicense("Mzc0ODEyQDMxMzgyZTM0MmUzMFE4NERZWWpyMDN6UTVJazVXQktCc2dxMlMxSnhGOHdqalo0ZXJiWmMzNFk9");

            //Create new instance of Schedule
            SfSchedule schedule = new SfSchedule();
            schedule.ScheduleView = ScheduleView.WeekView;
            schedule.TimeIntervalHeight = -1;
            //Customize the schedule view header
            ViewHeaderStyle viewHeaderStyle = new ViewHeaderStyle();
            viewHeaderStyle.BackgroundColor = Color.FromHex("#009688");
            viewHeaderStyle.DayTextColor = Color.FromHex("#FFFFFF");
            viewHeaderStyle.DateTextColor = Color.FromHex("#FFFFFF");
            viewHeaderStyle.DayFontFamily = "Arial";
            viewHeaderStyle.DateFontFamily = "Arial";
            schedule.ViewHeaderStyle = viewHeaderStyle;

            schedule.ScheduleView = ScheduleView.WeekView;
            //Create new instance of WeekViewSettings
            WeekViewSettings weekViewSettings = new WeekViewSettings();
            WeekLabelSettings weekLabelSettings = new WeekLabelSettings();
            weekLabelSettings.TimeFormat = "hh:mm";
            weekViewSettings.WorkStartHour = 0.5;
            weekViewSettings.WorkEndHour = 0.5;
            weekViewSettings.StartHour = 0.0;
            weekViewSettings.EndHour = 1.0;
            weekViewSettings.WeekLabelSettings = weekLabelSettings;
            schedule.WeekViewSettings = weekViewSettings;


            //Create new instance of SelectionStyle
            SelectionStyle selectionStyle = new SelectionStyle();
            selectionStyle.BackgroundColor = Color.Blue;
            selectionStyle.BorderColor = Color.Black;
            selectionStyle.BorderThickness = 5;
            selectionStyle.BorderCornerRadius = 5;
            schedule.SelectionStyle = selectionStyle;
            schedule.CellTapped += schedule_CellTappedday;
            ContentPage pickPage = new ContentPage { Content = schedule };
            pickPage.Title = "Pick Up Day";
            pickPage.Content.HorizontalOptions = LayoutOptions.CenterAndExpand;
            NavigationPage pickUpPage = new NavigationPage(pickPage);
            pickUpPage.BarBackgroundColor = Color.FromHex("#1c2f74");
            await Navigation.PushModalAsync(pickUpPage);
        }

        private async void schedule_CellTappedday(object sender, CellTappedEventArgs e)
        {
            SfSchedule schedule = new SfSchedule();
            schedule.ScheduleView = ScheduleView.DayView;
            schedule.TimeInterval = 180;
            DayViewSettings dayViewSetting = new DayViewSettings();
            DayLabelSettings dayLabelSettings = new DayLabelSettings();
            dayLabelSettings.TimeFormat = "hh:mm";
            dayViewSetting.WorkStartHour = 6.0;
            dayViewSetting.WorkEndHour = 12.0;
            dayViewSetting.DayLabelSettings = dayLabelSettings;

            schedule.DayViewSettings = dayViewSetting;

            //Customize the schedule view header
            ViewHeaderStyle viewHeaderStyle = new ViewHeaderStyle();
            viewHeaderStyle.BackgroundColor = Color.FromHex("#009688");
            viewHeaderStyle.DayTextColor = Color.FromHex("#FFFFFF");
            viewHeaderStyle.DateTextColor = Color.FromHex("#FFFFFF");
            viewHeaderStyle.DayFontFamily = "Arial";
            viewHeaderStyle.DateFontFamily = "Arial";
            schedule.ViewHeaderStyle = viewHeaderStyle;
            /////////////////////////////////////////////////////////
            schedule.MoveToDate = e.Datetime;
            /////////////////////////////////////////////////////////
            schedule.CellTapped += schedule_CellTapped;

            ContentPage pickPage = new ContentPage { Content = schedule };
            pickPage.Title = "Pick Up Time";
            pickPage.Content.HorizontalOptions = LayoutOptions.CenterAndExpand;
            NavigationPage pickUpPage = new NavigationPage(pickPage);
            pickUpPage.BarBackgroundColor = Color.FromHex("#1c2f74");
            await Navigation.PushModalAsync(pickUpPage);
        }

        private async void schedule_CellTapped(object sender, CellTappedEventArgs e)
        {
            Object_Classes.Transaction.pickupTime = e.Datetime;
            //await App.Current.MainPage.DisplayAlert("Alert", e.Datetime.ToString(), "OK");
            Syncfusion.Licensing.SyncfusionLicenseProvider.RegisterLicense("Mzc0ODEyQDMxMzgyZTM0MmUzMFE4NERZWWpyMDN6UTVJazVXQktCc2dxMlMxSnhGOHdqalo0ZXJiWmMzNFk9");
      
            //Create new instance of Schedule
            SfSchedule schedule = new SfSchedule();
            schedule.ScheduleView = ScheduleView.WeekView;
            schedule.TimeIntervalHeight = -1;
            //Customize the schedule view header
            ViewHeaderStyle viewHeaderStyle = new ViewHeaderStyle();
            viewHeaderStyle.BackgroundColor = Color.FromHex("#009688");
            viewHeaderStyle.DayTextColor = Color.FromHex("#FFFFFF");
            viewHeaderStyle.DateTextColor = Color.FromHex("#FFFFFF");
            viewHeaderStyle.DayFontFamily = "Arial";
            viewHeaderStyle.DateFontFamily = "Arial";
            schedule.ViewHeaderStyle = viewHeaderStyle;
            //Create new instance of WeekViewSettings
            WeekViewSettings weekViewSettings = new WeekViewSettings();
            WeekLabelSettings weekLabelSettings = new WeekLabelSettings();
            weekLabelSettings.TimeFormat = "hh:mm";
            weekViewSettings.WorkStartHour = 0.5;
            weekViewSettings.WorkEndHour = 0.5;
            weekViewSettings.StartHour = 0.0;
            weekViewSettings.EndHour = 1.0;
            weekViewSettings.WeekLabelSettings = weekLabelSettings;
            schedule.WeekViewSettings = weekViewSettings;


            //Create new instance of SelectionStyle
            SelectionStyle selectionStyle = new SelectionStyle();
            selectionStyle.BackgroundColor = Color.Blue;
            selectionStyle.BorderColor = Color.Black;
            selectionStyle.BorderThickness = 5;
            selectionStyle.BorderCornerRadius = 5;
            schedule.SelectionStyle = selectionStyle;
            schedule.CellTapped += schedule_CellTappedday2;
            ContentPage dropPage = new ContentPage { Content = schedule };
            dropPage.Title = "Drop Off Day";
            dropPage.Content.HorizontalOptions = LayoutOptions.CenterAndExpand;
            NavigationPage dropOffPage = new NavigationPage(dropPage);
            dropOffPage.BarBackgroundColor = Color.FromHex("#1c2f74");
            await Navigation.PushModalAsync(dropOffPage);
        }

        private async void schedule_CellTappedday2(object sender, CellTappedEventArgs e)
        {
            Object_Classes.Transaction.dropoffTime = e.Datetime;
            if ((Object_Classes.Transaction.pickupTime.ToShortDateString().Equals(Object_Classes.Transaction.dropoffTime.ToShortDateString())))
            {
                await App.Current.MainPage.DisplayAlert("Alert", "Drop off must be atleast 24 hours after pick up time", "OK");
            }
            else if ((Object_Classes.Transaction.dropoffTime < Object_Classes.Transaction.pickupTime))
                await App.Current.MainPage.DisplayAlert("Alert", "Drop off must be after pickup date", "OK");

            else
            {
                SfSchedule schedule = new SfSchedule();
                schedule.ScheduleView = ScheduleView.DayView;
                schedule.TimeInterval = 180;
                DayViewSettings dayViewSetting = new DayViewSettings();
                DayLabelSettings weekLabelSettings = new DayLabelSettings();
                weekLabelSettings.TimeFormat = "hh:mm";
                dayViewSetting.WorkStartHour = 16.0;
                dayViewSetting.WorkEndHour = 22.0;
                dayViewSetting.DayLabelSettings = weekLabelSettings;
                schedule.DayViewSettings = dayViewSetting;

                /////////////////////////////////////////////////////////
                schedule.MoveToDate = e.Datetime;
                /////////////////////////////////////////////////////////
                schedule.CellTapped += schedule_CellTapped2;

                ContentPage pickPage = new ContentPage { Content = schedule };
                pickPage.Title = "Drop off Time";
                pickPage.Content.HorizontalOptions = LayoutOptions.CenterAndExpand;
                NavigationPage pickUpPage = new NavigationPage(pickPage);
                pickUpPage.BarBackgroundColor = Color.FromHex("#1c2f74");
                await Navigation.PushModalAsync(pickUpPage);
            }
        }
        private async void schedule_CellTapped2(object sender, CellTappedEventArgs e)
        {
            Object_Classes.Transaction.dropoffTime = e.Datetime;
            NavigationPage ccPage = new NavigationPage(new Payment());
            ccPage.BarBackgroundColor = Color.FromHex("#1c2f74");
            Navigation.PushModalAsync(ccPage);
        }

        private async void SpecialButton_OnClicked(object sender, EventArgs e)
        {
            Object_Classes.Transaction.specialOrder = await DisplayPromptAsync("Order Instructions", "Enter your preferences below:");
        }

        private void Laundry_Button_Clicked(object sender, EventArgs e)
        {
            PopupNavigation.PushAsync(new LaundryPopup());
        }

        private void Dry_Button_Clicked(object sender, EventArgs e)
        {
            PopupNavigation.PushAsync(new DryCleanPopup());
        }
        private void laundryDry_Button_Clicked(object sender, EventArgs e)
        {
            PopupNavigation.PushAsync(new LaundryDryPopup());
        }

        private void callBox_CheckedChanged(object sender, CheckedChangedEventArgs e)
        {
            if (callBox.IsChecked)
                Object_Classes.Transaction.callDrop = true;
            else
                Object_Classes.Transaction.callDrop = false;
        }

        private void ringBox_CheckedChanged(object sender, CheckedChangedEventArgs e)
        {
            if (ringBox.IsChecked)
                Object_Classes.Transaction.ringDrop = true;
            else
                Object_Classes.Transaction.ringDrop = false;
        }

        private void textBox_CheckedChanged(object sender, CheckedChangedEventArgs e)
        {
            if (textBox.IsChecked)
                Object_Classes.Transaction.textDrop = true;
            else
                Object_Classes.Transaction.textDrop = false;
        }
    }
}