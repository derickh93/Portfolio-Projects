using NYC_Inspections.Views;
using System;
using Xamarin.Forms;

namespace NYC_Inspections
{
    public partial class AppShell : Xamarin.Forms.Shell
    {
        public AppShell()
        {
            InitializeComponent();
            Shell.SetTabBarIsVisible(this, false);
            Routing.RegisterRoute(nameof(List), typeof(List));
            Routing.RegisterRoute(nameof(Contact), typeof(Contact));
        }
    }
}