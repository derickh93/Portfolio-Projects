using System;
using System.Collections.Generic;
using System.Text;

namespace LaundryPickupNYC
{
    public class Invoice
    {
        public string created { get; set; }
        public string status { get; set; }

        public string duedate { get; set; }
        public string hostedInvoice { get; set; }
        public string invoicePDF { get; set; }
        public long amtdue { get; set; }

        public long amtpaid { get; set; }

        public long amtrem { get; set; }

        public string custname { get; set; }

        public string custphone { get; set; }

        public string custemail { get; set; }

        public string custaddr { get; set; }
        public bool paid { get; set; }



        public override string ToString()
        {
            return ("Invoice Created: " + created + "\nInvoice Status: " + status);
        }
    }
}
