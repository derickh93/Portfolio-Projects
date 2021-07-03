using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryPickupNYC.Models
{
    class CurrentOrderItem
    { 
    public string Created { get; set; }
    public string Total { get; set; }
    public string Status { get; set; }
    List<CurrentOrderItem> orders;


    public CurrentOrderItem()
    {

    }

    public CurrentOrderItem(string created, string total, string status)
    {
        Created = created;
        Total = total;
        Status = status;
    }

    public void addOrder(CurrentOrderItem pi)
    {
        orders.Add(pi);

    }

    public List<CurrentOrderItem> GetOrderItems()
    {
        orders = new List<CurrentOrderItem>()
        {
        };
        return orders;
    }
}

}
