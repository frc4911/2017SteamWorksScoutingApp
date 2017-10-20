﻿CREATE TABLE [dbo].[ScoutingDataFileLog]
(
	[Id] INT NOT NULL IDENTITY,
	[File] NVARCHAR(1024) NOT NULL,
	[TimeStamp] DATETIME NOT NULL,
	[Md5Hash] BINARY(16) NOT NULL,

	CONSTRAINT [PK_ScoutingDataFileLog] PRIMARY KEY ([Id]), 
)
