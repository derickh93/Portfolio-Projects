using NYC_Inspections.Models;
using System.Collections.Generic;

namespace NYC_Inspections.ViewModels
{
    internal class RestaurantItemViewModel
    {
        public List<RestaurantItem> RestaurantItems { get; set; }

        public RestaurantItemViewModel()
        {
            RestaurantItems = new RestaurantItem().GetRestaurantItems();
        }
    }
}