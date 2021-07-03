using Newtonsoft.Json;
using Rg.Plugins.Popup.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class PopupNewTaskView
    {
        FirebaseHelper fh = new FirebaseHelper();
        static readonly HttpClient client = new HttpClient();

        public PopupNewTaskView()
        {
            InitializeComponent();
        }

        private async void Button_OnClicked(object sender, EventArgs e)
        {

            string usps_api = "http://production.shippingapis.com/ShippingAPI.dll?API=Verify&XML";
            string usps_query = "=<AddressValidateRequest%20%20USERID=\"78QUEEN5333\"><AddressID="+"\"0\""+ "><Address1>" + addr1.Text + "</Address1><Address2>" + addr2.Text + "</Address2><City>" + city.Text + "</City><State>" + state.Text + " </State><Zip5>" + zip.Text + "</Zip5><Zip4></Zip4></Address></AddressValidateRequest>";

            await App.Current.MainPage.DisplayAlert("Alert", usps_api + usps_query, "OK");


            //validateAddress(usps_api, usps_query);

            GlobalVar.user.custAddress.Add(new Object_Classes.Address(addr1.Text,addr2.Text,city.Text,state.Text,zip.Text));

            Object_Classes.CollectionHelper.addressCollection.Add(new Object_Classes.Address(addr1.Text, addr2.Text, city.Text, state.Text, zip.Text));


            GlobalVar.user.jsonList = JsonConvert.SerializeObject(GlobalVar.user.custAddress);
            await fh.UpdatePerson(GlobalVar.user);
            await PopupNavigation.PopAsync();
        }

        public static async void validateAddress(string api, string path)
        {
            // Call asynchronous network methods in a try/catch block to handle exceptions.
            try
            {
                HttpResponseMessage response = await client.GetAsync(api + path);
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();
                // Above three lines can be replaced with new helper method below
                // string responseBody = await client.GetStringAsync(uri);

                await App.Current.MainPage.DisplayAlert("Alert", responseBody, "OK");

            }
            catch (HttpRequestException e)
            {
                await App.Current.MainPage.DisplayAlert("Alert", e.Message, "OK");

            }
        }
    }
}