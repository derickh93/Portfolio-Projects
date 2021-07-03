using NYC_Inspections.Models.NYC_Inspections.Models;
using System.Collections.Generic;

namespace NYC_Inspections.ViewModels
{
    internal class InspectionListViewModel
    {
        public List<InspectionItem> InspectionItems { get; set; }

        public InspectionListViewModel()
        {
            InspectionItems = new InspectionItem().GetInspectionItems();
        }
    }
}