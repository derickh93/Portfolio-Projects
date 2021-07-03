namespace NYC_Inspections.Models
{
    using System;
    using System.Collections.Generic;

    namespace NYC_Inspections.Models
    {
        internal class InspectionItem
        {
            public DateTime InspectionDate { set; get; }
            public string InspectionDateStr { set; get; }
            public string InspectionType { get; set; }

            public string Detail { get; set; }

            private List<InspectionItem> inspections;

            public InspectionItem()
            {
            }

            public InspectionItem(DateTime inspectionDate,  string inspectionType)
            {
                InspectionDate = inspectionDate;
                InspectionType = inspectionType;
                Detail = InspectionType;
                InspectionDateStr = InspectionDate.ToShortDateString();
            }

            public List<InspectionItem> GetInspectionItems()
            {
                inspections = new List<InspectionItem>()
                {
                };
                return inspections;
            }
        }
    }
}