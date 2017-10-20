CREATE TABLE [dbo].[Teams] (
    [TeamNumber]   INT             NOT NULL,
    [ShortName]    NVARCHAR (128)  NOT NULL,
    [FullName]     NVARCHAR (1024) NOT NULL,
    [City]         NVARCHAR (50)   NOT NULL,
    [StateProv]    NVARCHAR (50)   NOT NULL,
    [Country]      NVARCHAR (50)   NOT NULL,
    [DistrictCode] NVARCHAR (50)   NOT NULL,
    [RookieYear]   INT             NOT NULL,
    [RobotName]    NVARCHAR (128)  NULL,
    [WebSite]      NVARCHAR (2048) NULL,
    [SchoolName]   NVARCHAR (128)  NULL,
    [HomeCMP]      NVARCHAR (50)   NULL,
    CONSTRAINT [PK_TeamsTeamNumber] PRIMARY KEY CLUSTERED ([TeamNumber] ASC)
);

