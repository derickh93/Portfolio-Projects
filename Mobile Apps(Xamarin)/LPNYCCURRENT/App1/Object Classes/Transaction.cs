using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryPickupNYC.Object_Classes
{
    public static class Transaction
    {

        public static  Object_Classes.Address address;
        public static string specialOrder;
        public static  string orderQuantity;
        public static  DateTime pickupTime;
        public static  DateTime dropoffTime;
        public static bool textDrop = false;
        public static bool callDrop = false;
        public static bool ringDrop = false;
    }
}
