using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.Models
{
    class OrderItem
    {
        public string Created { get; set; }
        public string Total { get; set; }
        public string Status { get; set; }
        List<OrderItem> orders;


        public OrderItem()
        {

        }

        public OrderItem(string created, string total,string status)
        {
            Created = created;
            Total = total;
            Status = status;
        }

        public void addOrder(OrderItem pi)
        {
            orders.Add(pi);

        }

        public List<OrderItem> GetOrderItems()
        {
            orders = new List<OrderItem>()
            {
            };
            return orders;
        }
    }

}
