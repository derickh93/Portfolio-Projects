using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.Models
{
    class ProductItem
    {
        public string Name { get; set; }
        public string Description { get; set; }

        List<ProductItem> products;

        public ProductItem()
        {

        }

        public ProductItem(string name, string description)
        {
                Name = name;
                Description = description;
        }

        public void addProduct(ProductItem pi)
        {
            products.Add(pi);

        }

        public List<ProductItem> GetProductItems()
        {
            products = new List<ProductItem>()
            {
            };
            return products;
        }

    }
}
