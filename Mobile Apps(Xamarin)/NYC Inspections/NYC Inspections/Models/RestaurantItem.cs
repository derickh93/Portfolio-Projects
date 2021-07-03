using NYC_Inspections.Services;
using System;
using System.Collections.Generic;
using Xamarin.Essentials;
using Xamarin.Forms;

namespace NYC_Inspections.Models
{
    internal class RestaurantItem
    {
        public string Dba { get; set; }
        public string Cuisine { get; set; }
        public string Camis { get; set; }
        public string Grade { get; set; }
        public int Index { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public double Distance { get; set; }

        public DateTime Inspection_Date { get; set; }


        public ImageSource ImageUri { get; set; }

        private List<RestaurantItem> gradings;

        public RestaurantItem()
        {
        }

        public RestaurantItem(string dba, string cuisine, ImageSource img, string camis, string grade, int index,double latitude,double longitude,DateTime inspectionDate)
        {
            Dba = dba;
            Cuisine = cuisine;
            ImageUri = img;
            Camis = camis;
            Grade = grade;
            Index = index;
            Longitude = longitude;
            Latitude = latitude;
            Inspection_Date = inspectionDate;
            Distance = calculateDistance();
                
        }

        public override string ToString()
        {
            return Dba + " " + Cuisine + " " + ImageUri + " "+ Camis + " "  + Grade + " " + Index + " " +  Distance + " " + Inspection_Date;
        }

        private double calculateDistance()
        {
            Location sourceCoordinates = new Location(GlobalVar.latitude, GlobalVar.longitude);
            Location destinationCoordinates = new Location(Latitude, Longitude);
            double distance = Location.CalculateDistance(sourceCoordinates, destinationCoordinates, DistanceUnits.Miles);
            return distance;
        }

        public bool Equals(RestaurantItem obj)
        {
            return this.Camis.Equals(obj.Camis);
        }

        public List<RestaurantItem> GetRestaurantItems()
        {
            gradings = new List<RestaurantItem>()
            {
            };
            return gradings;
        }
    }
}