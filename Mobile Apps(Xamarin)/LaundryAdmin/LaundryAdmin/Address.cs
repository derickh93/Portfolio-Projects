using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;

namespace App1.Object_Classes
{
    public class Address
    { 
    
        public Address(string addr1, string addr2, string city, string state, string zip)
        {
            address1 = addr1;
            address2 = addr2;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }
        public string address1 { get; set; }

        public string address2 { get; set; }

        public string city { get; set; }

        public string state { get; set; }

        public string zip { get; set; }

        public override string ToString()
        {
            return address1 + ", " + address2 + ", " + city + ", " + state + " " + zip;
        }




    }
}
