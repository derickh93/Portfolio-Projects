using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace LaundryPickupNYC.Models
{
    class PriceItem
    {
        public string Text { set; get; }
        public string TextColor { set; get; }
        public string Price { set; get; }
        public string PriceColor { set; get; }
        public FontAttributes FontAt { set; get; }


        List<PriceItem> prices;


    public PriceItem()
    {

    }

    public PriceItem(string text, string textColor, string price, string priceColor, FontAttributes fontAt)
    {
        Text = text;
        TextColor = textColor;
        Price = price;
        PriceColor = priceColor;
            FontAt = fontAt;
    }

    public void addPrice(PriceItem pi)
    {
        prices.Add(pi);

    }

    public List<PriceItem> GetPriceItems()
    {
        prices = new List<PriceItem>()
        {
        };
        return prices;
    }
}

}

