
using App1.Object_Classes;
using System;
using System.Collections.Generic;
using System.Text;

namespace App1
{
    public class Person
    {
        public Person()
        {
        }
        public Guid PersonId { get; set; }
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string phone { get; set; }
        public string email { get; set; }
        public string userID { get; set; }
        public string customerID { get; set; }

        public List<Address> custAddress = new List<Address>();
        public string jsonList;
    }
}