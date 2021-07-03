using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using LaundryPickupNYC.Droid;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xamarin.Forms;

[assembly: Dependency(typeof(Caminho))]
namespace LaundryPickupNYC.Droid
{
    public class Caminho : ICaminho
    {
        public string GetPath()
        {
            return Android.App.Application.Context.GetExternalFilesDir("").AbsolutePath;
        }
    }
}