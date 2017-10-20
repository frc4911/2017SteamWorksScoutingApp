CREATE TABLE [dbo].[Events] (
	[Season]	   INT NOT NULL,
    [EventCode]    NVARCHAR (50)   NOT NULL,
    [DistrictCode] NVARCHAR (50)   NOT NULL,
    [DivisionCode] NVARCHAR (50)   NOT NULL,
    [Name]         NVARCHAR (50)   NOT NULL,
    [Type]         NVARCHAR (50)   NOT NULL,
    [Venue]        NVARCHAR (128)  NOT NULL,
    [City]         NVARCHAR (128)  NOT NULL,
    [Address]      NVARCHAR (128)  NULL,
    [StateProv]    NVARCHAR (50)   NOT NULL,
    [Country]      NVARCHAR (50)   NOT NULL,
    [TimeZone]     NVARCHAR (50)   NULL,
    [DateStart]    DATETIME        NOT NULL,
    [DateEnd]      DATETIME        NOT NULL,
    [WebSite]      NVARCHAR (2048) NULL,
    CONSTRAINT [PK_EventsSeasonEventCode] PRIMARY KEY CLUSTERED ([Season], [EventCode] ASC)
);

