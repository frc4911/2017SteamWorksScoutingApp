# ScoutingServer
Server components of Scouting App

* Scouting Database as the data repository
* Scouting Service receives data and updates the database after each match.

## Scouting Database
This project assumes SQL Server Express is installed on development and scouting laptop.

### Install SQL Server Express and SQL Server Management Studio

1. Download SQL Server Express 2016 from https://www.microsoft.com/en-us/sql-server/sql-server-editions 
2. Choose Basic installation
3. Install SQL Server Management Studio from the download page above


## Scouting Service

The Scouting Service implements a Bluetooth listner using the [32feet.NET](https://github.com/inthehand/32feet) package.
The JSON payloads received from the Scouting are modelled using [Newtonsoft.Json](https://www.newtonsoft.com/json).

The [Microsoft EntityFramework](http://go.microsoft.com/fwlink/?LinkID=320540) is used for data access and modelling between database and service.

The [Z.EntityFramework.Extensions](http://entityframework-extensions.net/?z=nuget) for improved bulk save performance.  Please note that 
this package must be updated every 30 days as the [trial period expires](http://entityframework-extensions.net/trial-period-expired-exception).

To use the FRCEvents library, you must supply your registered username and authorization key. See [How to register for FRC Event API](http://docs.frcevents2.apiary.io/#reference/authorization/retrieve-event-alliance-selection) for more details.
