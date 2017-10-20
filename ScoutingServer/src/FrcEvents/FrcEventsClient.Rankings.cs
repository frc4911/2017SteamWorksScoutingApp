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
        /// Gets the team ranking detail from a particular event in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="top">Number of requested top ranked teams to return in result.</param>
        public async Task<IEnumerable<TeamRanking>> GetRankings(int season, string eventCode, int? top = null)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/rankings/{1}", season, eventCode);
            if (top.HasValue && top.Value > 0)
            {
                path = string.Concat(path, string.Format(CultureInfo.InvariantCulture, "?top={0}", top.Value));
            }

            return await GetRankings(path);
        }

        /// <summary>
        /// Gets the team ranking detail from a particular event in a particular season for a particular team.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="teamNumber">Team number of the team whose ranking is requested.</param>
        public async Task<IEnumerable<TeamRanking>> GetRankingsByTeamNumber(int season, string eventCode, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);

            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/rankings/{1}?teamNumber={2}", season, eventCode, teamNumber);
            return await GetRankings(path);
        }
    }
}
