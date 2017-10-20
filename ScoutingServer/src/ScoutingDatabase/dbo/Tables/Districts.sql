CREATE TABLE [dbo].[Districts] (
	[Season]	   INT		      NOT NULL,
    [DistrictCode] NVARCHAR (50)  NOT NULL,
    [Name]         NVARCHAR (256) NOT NULL,
    CONSTRAINT [PK_SeasonDistrictCodeDistrictCode] PRIMARY KEY CLUSTERED ([Season], [DistrictCode] ASC)
);

