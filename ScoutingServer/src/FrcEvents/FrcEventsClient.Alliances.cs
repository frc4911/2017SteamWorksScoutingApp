//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;
using Org.USFirst.Frc.Team4911.FrcEvents.Models;

namespace Org.USFirst.Frc.Team4911.FrcEvents
{
    public partial class FrcEventsClient
    {
        /// <summary>
        /// Retrieve details about alliance selection at a particular event in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <returns></returns>
        public async Task<IEnumerable<Alliance>> GetAlliances(int season, string eventCode)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/alliances/{1}", season, eventCode);
            return await GetAlliances(path);
        }
    }
}
