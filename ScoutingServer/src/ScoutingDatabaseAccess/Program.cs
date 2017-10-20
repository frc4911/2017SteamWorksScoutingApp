//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Linq;

namespace Org.USFirst.Frc.Team4911.ScoutingDatabaseAccess
{
    class Program
    {
        static void Main(string[] args)
        {
            using (var db = new ScoutingEntities())
            {
                var newTeam = new Team()
                {
                    TeamNumber = 7089,
                    ShortName = "Robotos",
                    FullName = "a Team form somewhere closeby",
                    City = "Kirkland",
                    Country = "US",
                    StateProv = "WA",
                    RookieYear = 2017,
                    DistrictCode = "PNW",
                    RobotName = string.Empty,
                    WebSite = string.Empty
                };

                try
                {
                    db.Teams.Add(newTeam);
                    db.SaveChanges();
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex);
                }

                // Display all Blogs from the database 
                var query = from team in db.Teams
                            orderby team.ShortName
                            select team;

                Console.WriteLine("All teams in the database:");
                foreach (var item in query)
                {
                    Console.WriteLine(item.ShortName + " " + item.FullName);
                }

                Console.WriteLine("Press any key to exit...");
                Console.ReadKey();

                try
                {
                    newTeam.FullName = "A team closeby";
                    db.SaveChanges();
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex);
                }

                // Display all Blogs from the database 
                query = from team in db.Teams
                        orderby team.ShortName
                        select team;

                Console.WriteLine("All teams in the database:");
                foreach (var item in query)
                {
                    Console.WriteLine(item.ShortName + " " + item.FullName);
                }

                Console.WriteLine("Press any key to exit...");
                Console.ReadKey();

                try
                {
                    db.Teams.Remove(newTeam);
                    Console.WriteLine(db.Teams.Count());
                    db.SaveChanges();
                    //db.Delete_Team(newTeam.TeamNumber);
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex);
                }

                // Display all Blogs from the database 
                query = from team in db.Teams
                        orderby team.ShortName
                        select team;

                Console.WriteLine("All teams in the database:");
                foreach (var item in query)
                {
                    Console.WriteLine(item.ShortName + " " + item.FullName);
                }

                Console.WriteLine("Press any key to exit...");
                Console.ReadKey();

            }
        }
    }
}
