//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Org.USFirst.Frc.Team4911.ScoutingService.DatabaseAccess
{
    using System;
    using System.Collections.Generic;
    
    public partial class ScoutingDataTeleop
    {
        public int Id { get; set; }
        public int ScoutingDataId { get; set; }
        public Nullable<byte> TeleopPlayedDefense { get; set; }
        public Nullable<int> GearAttemptCount { get; set; }
        public Nullable<int> ShotAttemptCount { get; set; }
    
        public virtual ScoutingDataMatch ScoutingDataMatch { get; set; }
    }
}
