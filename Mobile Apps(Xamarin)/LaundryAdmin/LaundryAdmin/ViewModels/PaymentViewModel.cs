using LaundryAdmin.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.ViewModels
{
    class PaymentViewModel
    {
        public List<PaymentItem> PayItems { get; set; }
        public PaymentViewModel()
        {
            PayItems = new PaymentItem().GetPaymentItems();
        }
    }
}
