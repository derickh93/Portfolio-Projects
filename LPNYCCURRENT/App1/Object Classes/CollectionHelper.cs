using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace LaundryPickupNYC.Object_Classes
{
    public static class CollectionHelper
    {
        public static ObservableCollection<LaundryPickupNYC.Object_Classes.Address> addressCollection = new ObservableCollection<Object_Classes.Address>();
        public static ObservableCollection<string> creditCollection;
    }
}
