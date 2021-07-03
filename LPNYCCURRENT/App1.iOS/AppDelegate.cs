using System;
using System.Collections.Generic;
using System.Linq;

using Foundation;
using Syncfusion.SfCarousel.XForms.iOS;
using Syncfusion.SfSchedule.XForms.iOS;
using UIKit;

namespace LaundryPickupNYC.iOS
{
    // The UIApplicationDelegate for the application. This class is responsible for launching the 
    // User Interface of the application, as well as listening (and optionally responding) to 
    // application events from iOS.
    [Register("AppDelegate")]
    public partial class AppDelegate : global::Xamarin.Forms.Platform.iOS.FormsApplicationDelegate
    {
        //
        // This method is invoked when the application has loaded and is ready to run. In this 
        // method you should instantiate the window, load the UI into it and then make the window
        // visible.
        //
        // You have 17 seconds to return from this method, or iOS will terminate your application.
        //
        public override bool FinishedLaunching(UIApplication app, NSDictionary options)
        {
            Rg.Plugins.Popup.Popup.Init();
            Syncfusion.Licensing.SyncfusionLicenseProvider.RegisterLicense("Mzc0ODEyQDMxMzgyZTM0MmUzMFE4NERZWWpyMDN6UTVJazVXQktCc2dxMlMxSnhGOHdqalo0ZXJiWmMzNFk9");
            global::Xamarin.Forms.Forms.Init();
            new SfCarouselRenderer();
            Syncfusion.SfPdfViewer.XForms.iOS.SfPdfDocumentViewRenderer.Init();
            Syncfusion.SfRangeSlider.XForms.iOS.SfRangeSliderRenderer.Init();
            SfScheduleRenderer.Init();
            LoadApplication(new App());

            return base.FinishedLaunching(app, options);
        }
    }
}
