using Stripe;
using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryAdmin.Models
{
    class PaymentItem
    {
        public string Amount { get; set; }
        public string Created { get; set; }
        public string Customer { get; set; }
        public string Status { get; set; }

        List<PaymentItem> payments;

        public PaymentItem()
        {

        }

        public PaymentItem(string amount, string created, string customer, string status)
        {

            try
            {
                var serviceName = new CustomerService();
                var cust = serviceName.Get(customer);
                string custName = cust.Name.ToString();

                int originalValue = Int32.Parse(amount);
                string yourValue = (originalValue / 100m).ToString("C2");

                Amount = yourValue;
                Created = created;
                Customer = custName;
                Status = status;
            }
            catch
            {
                Amount = amount;
                Created = created;
                Customer = "Testing";
                Status = status;
            }
        }

        public void addCustomer(PaymentItem pi)
        {
            payments.Add(pi);

        }

        public List<PaymentItem> GetPaymentItems()
        {
            payments = new List<PaymentItem>()
            {
            };
            return payments;
        }


    }
}
