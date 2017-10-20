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
        /// Gets the all registrations for teams in a particular season at particular event (and tournamnet level.)
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        public async Task<IEnumerable<ScheduledMatch>> GetEventSchedule(int season, string eventCode, TournamentLevel tournamentLevel)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);

            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/schedule/{1}?tournamentLevel={2}", season, eventCode, tournamentLevel.ToString());
            return await GetEventSchedule(path);
        }

        /// <summary>
        /// Gets the all registrations for teams in a particular season at particular event (and tournamnet level) for a particlular team.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        /// <param name="teamNumber">Number of requested top ranked teams to return in result.</param>
        public async Task<IEnumerable<ScheduledMatch>> GetEventSchedule(int season, string eventCode, TournamentLevel tournamentLevel, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/schedule/{1}?tournamentLevel={2}&teamNumber={3}", season, eventCode, tournamentLevel.ToString(), teamNumber);
            return await GetEventSchedule(path);
        }
    }
}
