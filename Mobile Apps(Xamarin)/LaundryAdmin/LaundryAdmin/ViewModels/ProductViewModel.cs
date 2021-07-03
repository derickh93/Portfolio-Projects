using LaundryAdmin.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.ViewModels
{
    class ProductViewModel
    { 
        public List<ProductItem> ProductItems { get; set; }
    public ProductViewModel()
    {
        ProductItems = new ProductItem().GetProductItems();
    }
}
}
