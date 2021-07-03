using LaundryPickupNYC.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryPickupNYC.ViewModels
{
    class CurrentOrderViewModel
    { 
    public List<CurrentOrderItem> CurrentOrderItems { get; set; }
    public CurrentOrderViewModel()
    {
        CurrentOrderItems = new CurrentOrderItem().GetOrderItems();
    }
}
}