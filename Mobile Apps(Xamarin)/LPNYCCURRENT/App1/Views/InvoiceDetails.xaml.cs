using LaundryPickupNYC.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryPickupNYC
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class InvoiceDetails : ContentPage
    {
        public InvoiceDetails(Stripe.Invoice inv)
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);
            try
            {   
                    DateTime dt = (DateTime)inv.DueDate;
                    dueDate.Text = "Due Date: " + dt.ToShortDateString();               
            }
            catch
            {
                dueDate.Text = "Due Date: N/A";
            }
            try
            {
                string value = "";
                if (inv.Metadata.TryGetValue("Status", out value))
                {
                    status.Text = "Status: " + value;
                }
            }
            catch
            {
                status.Text = "Status: N/A";
            }

            try
            {
                descrip.Text = "Description: " + inv.Description;
            }
            catch
            {
                descrip.Text = "Description: N/A";
            }

            try
            {
                stmtDesc.Text = "Statement Description: " + inv.StatementDescriptor;

            }
            catch
            {
                stmtDesc.Text = "Statement Description: N/A";

            }

            try
            {
                string textProds = "products: ";
                for(int i = 0; i < inv.Lines.Data.Count;i++)
                {
                    textProds += inv.Lines.Data.ElementAt(i);
                }
                products.Text = textProds;
            }
            catch
            {
                products.Text = "products: N/A";
            }
            created.Text = "Invoice Created: " + inv.Created.ToShortDateString();
            amtDue.Text = "Amount Due: $" + inv.AmountDue*.01;
            amtPaid.Text = "Amount Paid: $" + inv.AmountPaid*.01;
            amtRem.Text = "Amount Remaining: $" + inv.AmountRemaining*.01;
            total.Text = "Total: $" + inv.Total*.01;
            paid.Text = "Paid: " + inv.Paid.ToString();
            invNum.Text = "Invoice Number: " + inv.Number;
        }

        private void Button_Clicked(object sender, EventArgs e)
        {
           Navigation.PushModalAsync(new NavigationPage(new Payment()));
        }
    }
}