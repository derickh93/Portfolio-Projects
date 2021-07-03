using LaundryPickupNYC.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryPickupNYC.ViewModels
{
    class PriceViewModel
    { 
    public List<PriceItem> PriceItem { get; set; }
    public PriceViewModel()
    {
        PriceItem = new PriceItem().GetPriceItems();
    }
}
}