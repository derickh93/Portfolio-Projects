using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.Models
{
    public class CustomerItem
    {
        public string Name { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }
        public long Balance { get; set; }

        List<CustomerItem> customers;

        public CustomerItem()
        {

        }

        public CustomerItem(string name, string phone, string email, long balance)
        {
            Name = name;
            Phone = phone;
            Email = email;
            Balance = balance;

        }

        public void addCustomer(CustomerItem ci)
        {
            customers.Add(ci);

        }

        public List<CustomerItem> GetCustomerItems()
        {
            customers = new List<CustomerItem>()
            {
            };
            return customers;
        }


    }
}
