using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryPickupNYC
{
    static class GlobalVar
    {
        public static bool loggedIn = false;
        public static Person user = new Person();
        public static bool loading = true;
    }
}
