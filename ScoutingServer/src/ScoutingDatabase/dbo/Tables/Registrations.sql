CREATE TABLE [dbo].[Registrations] (
	[Season]	 INT		   NOT NULL,
	[TeamNumber] INT           NOT NULL,
    [EventCode]  NVARCHAR (50) NOT NULL,
    CONSTRAINT [PK_RegistrationsSeasonTeamNumberEventCode] PRIMARY KEY CLUSTERED ([Season], [TeamNumber], [EventCode] ASC)
);

