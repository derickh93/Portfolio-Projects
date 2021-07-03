using LaundryAdmin.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.ViewModels
{
    class OrderViewModel
    {
        public List<OrderItem> OrderItems { get; set; }
        public OrderViewModel()
        {
            OrderItems = new OrderItem().GetOrderItems();
        }
    }
}
