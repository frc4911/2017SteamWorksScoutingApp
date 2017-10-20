//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Globalization;

namespace Org.USFirst.Frc.Team4911.FrcEvents
{
    public static class Validation
    {
        public static int EnsureSeason(int season)
        {
            if (season >= 2015 && season <= DateTime.Now.Year)
            {
                return season;
            }

            string msg = string.Format(
                CultureInfo.InvariantCulture,
                "'{0}' == '{1}' which is not in the range '{2}'..'{3}'.",
                "season",
                season,
                2015,
                DateTime.Now.Year);
            throw new ArgumentOutOfRangeException(nameof(season), season, msg);
        }

        public static string EnsureEventCode(string eventCode)
        {
            string msg;
            if (string.IsNullOrEmpty(eventCode))
            {
                msg = string.Format(
                    CultureInfo.InvariantCulture,
                    "'{0}' may not be null or empty",
                    "eventCode");

                throw new ArgumentNullException(nameof(eventCode), msg);
            }

            if (eventCode.Length >= 3)
            {
                return eventCode;
            }

            msg = string.Format(
                CultureInfo.InvariantCulture,
                "Argument '{0}' (value: {1}) must be at least 3 characters.",
                nameof(eventCode),
                eventCode);

            throw new ArgumentOutOfRangeException(nameof(eventCode), eventCode, msg);
        }

        public static int EnsureTeamNumber(int teamNumber)
        {
            if (teamNumber >= 1)
            {
                return teamNumber;
            }

            string msg = string.Format(
                CultureInfo.InvariantCulture,
                "'{0}' == '{1}' teamNumber must be greater than 0",
                nameof(teamNumber),
                teamNumber);

            throw new ArgumentOutOfRangeException(nameof(teamNumber), teamNumber, msg);
        }

        public static string EnsureDictrictCode(string districtCode)
        {
            string msg;
            if (string.IsNullOrEmpty(districtCode))
            {
                msg = string.Format(
                    CultureInfo.InvariantCulture,
                    "'{0}' may not be null or empty",
                    "eventCode");

                throw new ArgumentNullException(nameof(districtCode), msg);
            }

            if (districtCode.Length >= 2)
            {
                return districtCode;
            }

            msg = string.Format(
                CultureInfo.InvariantCulture,
                "Argument '{0}' (value: {1}) must be at least 3 characters.",
                nameof(districtCode),
                districtCode);

            throw new ArgumentOutOfRangeException(nameof(districtCode), districtCode, msg);
        }
    }
}
