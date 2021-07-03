using SODA;
using System;
using System.Collections.Generic;
using System.Linq;

using Xamarin.Forms;
using Xamarin.Forms.Maps;
using Xamarin.Forms.Xaml;

namespace NYC_Inspections.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Map : ContentPage
    {
        private IEnumerable<Dictionary<string, object>> tempquery;
        private int[] tempindex;
        private SODA.Resource<Dictionary<string, object>> tempds;
        private int clicked = 0;

        public Map(IEnumerable<Dictionary<string, object>> query, int[] index, SODA.Resource<Dictionary<string, object>> ds)
        {
            InitializeComponent();
            tempquery = query;
            tempds = ds;
            tempindex = index;
            var map = mapObj;
            List<Position> positions = new List<Position>();

            //////////////////////////////////////////////////////////
            for (int i = 0; i < index.Count(); i++)
            {
                try
                {
                    object latitude = "";
                    object longitude = "";
                    object dba = "";
                    object grade = "";
                    object cuisine = "";
                    object camis = "";


                    if (query.ElementAt(index[i]).TryGetValue("latitude", out latitude))
                    {
                    }
                    else
                    {
                        latitude = "0";
                    }

                    if (query.ElementAt(index[i]).TryGetValue("longitude", out longitude))
                    {
                    }
                    else
                    {
                        longitude = "0";
                    }
                    if (query.ElementAt(index[i]).TryGetValue("grade", out grade))
                    {
                    }
                    else
                    {
                        grade = "NULL";
                    }

                    if (query.ElementAt(index[i]).TryGetValue("dba", out dba))
                    {
                    }
                    else
                    {
                        dba = "NULL";
                    }

                    if (query.ElementAt(index[i]).TryGetValue("camis", out camis))
                    {
                    }
                    else
                    {
                        camis = "NULL";
                    }

                    if (query.ElementAt(index[i]).TryGetValue("cuisine_description", out cuisine))
                    {
                    }
                    else
                    {
                        cuisine = "NULL";
                    }
                    if ((Double.Parse(latitude.ToString()) != 0 || Double.Parse(longitude.ToString()) != 0))
                    {
                        clicked = index[i];
                        Pin pin = new Pin
                        {
                            Label = dba.ToString(),
                            Address = grade.ToString() + " - " + cuisine.ToString(),
                            Type = PinType.Place,
                            AutomationId = camis.ToString(),
                            Position = new Position(Double.Parse(latitude.ToString()), Double.Parse(longitude.ToString()))
                        };
                        map.Pins.Add(pin);
                        positions.Add(new Position(Double.Parse(latitude.ToString()), Double.Parse(longitude.ToString())));
                        pin.InfoWindowClicked += WindowClicked;
                    }
                }
                catch (Exception ex)
                {
                    DisplayAlert("test", ex.ToString(), "cancel");
                }
                Distance d = new Distance(1000);

                map.MoveToRegion(FromPositions(positions));
                clicked = map.Pins.Count();
            }
        }

        private async void WindowClicked(object sender, PinClickedEventArgs args)
        {
            var soql = new SoqlQuery().Where($"camis = '{((Pin)sender).AutomationId}'");
            var query = tempds.Query<Dictionary<string, object>>(soql);

            await Navigation.PushAsync(new InspectionList(query.ElementAt(0), tempds));
        }

        private static MapSpan FromPositions(IEnumerable<Position> positions)
        {
            double minLat = double.MaxValue;
            double minLon = double.MaxValue;
            double maxLat = double.MinValue;
            double maxLon = double.MinValue;

            foreach (var p in positions)
            {
                minLat = Math.Min(minLat, p.Latitude);
                minLon = Math.Min(minLon, p.Longitude);
                maxLat = Math.Max(maxLat, p.Latitude);
                maxLon = Math.Max(maxLon, p.Longitude);
            }

            return new MapSpan(
                new Position((minLat + maxLat) / 2d, (minLon + maxLon) / 2d),
                maxLat - minLat,
                maxLon - minLon);
        }
    }
}