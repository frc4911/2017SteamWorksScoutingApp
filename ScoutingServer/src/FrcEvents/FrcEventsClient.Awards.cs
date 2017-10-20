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
        /// Gets details about awards presented in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <returns></returns>
        public async Task<IEnumerable<Award>> GetAwards(int season)
        {
            season = Validation.EnsureSeason(season);
			var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/awards", season);
            return await GetAwards(path);
        }

        /// <summary>
        /// Gets details about awards presented in a particular season for a particlar team.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="teamNumber">Team number of the team whose ranking is requested.</param>
        /// <returns></returns>
        public async Task<IEnumerable<Award>> GetAwards(int season, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
			var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/awards/{1}", season, teamNumber);
            return await GetAwards(path);
        }

        /// <summary>
        /// Gets details about awards presented at a particular event in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <returns></returns>
        public async Task<IEnumerable<Award>> GetAwards(int season, string eventCode)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/awards/{1}", season, eventCode);
            return await GetAwards(path);
        }

        /// <summary>
        /// Gets details about awards presented at a particular event in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="teamNumber">Team number of the team whose ranking is requested.</param>
        /// <returns></returns>
        public async Task<IEnumerable<Award>> GetAwards(int season, string eventCode, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/awards/{1}/{2}", season, eventCode, teamNumber);
            return await GetAwards(path);
        }

        /// <summary>
        /// The award listings API returns a listing of the various awards that can be distributed in the requested season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <returns></returns>
        public async Task<IEnumerable<AwardListing>> GetAwardListings(int season)
        {
            season = Validation.EnsureSeason(season);
			var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/awards/list", season);
            return await GetAwardListings(path);
        }
    }
}
