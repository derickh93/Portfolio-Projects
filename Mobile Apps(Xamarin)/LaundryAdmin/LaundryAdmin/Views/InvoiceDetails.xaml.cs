using LaundryAdmin.Views;
using Stripe;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace LaundryAdmin
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class InvoiceDetails : ContentPage
    {
        Stripe.Invoice invoice;
        public InvoiceDetails(Stripe.Invoice inv)
        {
            InitializeComponent();
            NavigationPage.SetHasNavigationBar(this, false);

            statusPicker.Items.Add("Scheduled");
            statusPicker.Items.Add("Pickup");
            statusPicker.Items.Add("Ready");
            statusPicker.Items.Add("Delivered");
            invoice = inv;

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
                if (invoice.Metadata.TryGetValue("Status", out value))
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
                descrip.Text = "Description:";
            }

            try
            {
                stmtDesc.Text = "Statement Description: " + inv.StatementDescriptor;

            }
            catch
            {
                stmtDesc.Text = "Statement Description:";

            }

            try
            {
                products.Text = "products: " + inv.Lines.Data.ElementAt(0).Description;
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

            statusPicker.SelectedIndexChanged += OnPickerSelectedIndexChanged;
        }

        void OnPickerSelectedIndexChanged(object sender, EventArgs e)
        {
            var picker = (Picker)sender;
            int selectedIndex = picker.SelectedIndex;
            invoice.Metadata.Remove("Status");
            invoice.Metadata.Add("Status", picker.Items.ElementAt(selectedIndex));

            if (selectedIndex != -1)
            {
                var options = new InvoiceUpdateOptions
                {
                        Metadata = invoice.Metadata
                };
                var service = new InvoiceService();
                service.Update(
                  invoice.Id,
                  options
                );
            }
        }
    }
}