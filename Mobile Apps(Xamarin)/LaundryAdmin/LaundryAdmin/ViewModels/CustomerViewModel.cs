using LaundryAdmin.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.ViewModels
{
    public class CustomerViewModel
    {
        public List<CustomerItem> CustItems { get; set; }
    public CustomerViewModel()
        {
            CustItems = new CustomerItem().GetCustomerItems();
        }
    }
}
