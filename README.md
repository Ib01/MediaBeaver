# Installation

1) Get the latest version of the source

2) Use Maven to run a **clean install** on the mediaBeaver project (the parent project)

3) place **\mediabeaver\MediaBeaver\MediaBeaverServer\target\mediaBeaverServer-0.0.1-SNAPSHOT.war** and **\mediabeaver\MediaBeaver\MediaBeaverServer\target\mediaBeaverCli-0.0.1-SNAPSHOT.jar** 
on the target system. 

4) go to the command line

5) navigate to the directory containing **mediaBeaverCli-0.0.1-SNAPSHOT.jar**

6) execute **java -jar mediaBeaverCli-0.0.1-SNAPSHOT.jar -initialise** to create the database and populate it with some default configuration.

7) navigate to the directory containing **mediaBeaverServer-0.0.1-SNAPSHOT.war**

8) execute **java -jar mediaBeaverServer-0.0.1-SNAPSHOT.war** (this will start the web app)

9) navigate to the web app: using **http;//{ip address of your host}:8081/configuration**.  modify your configuration to suit (see below for assistance with this).

10) to move media on your server navigate to the directory containing **mediaBeaverCli-0.0.1-SNAPSHOT.jar** and execute **java -jar mediaBeaverCli-0.0.1-SNAPSHOT.jar -move**. Please note the lifecycle is start, move, finish. So a way to implement this would be to either run this via a scheduled task/cron or run this after the new files have been created.


# Getting started

1) Goto **http;//{ip address of your host}:8081/configuration**.  you should see the screen below.  

![MediaBeaverConfiguration.png](https://bitbucket.org/repo/5MgKjp/images/1103420040-MediaBeaverConfiguration.png)

2) Modify your data to suit.

If you followed step 6 above the app should already be populated with default data.  All you need to change to get started on a windows system are the TV Root and Movie Root paths (point these paths to wherever you keep your movies and TV shows). If you are using linux or Mac you will also need to change the path separators in the **Movie Path** and **Episode Path** fields. See below for details about these fields.

By default the application uses the following structure for TV shows: **{Series Name}\Season {Season Number}\{Series Name} S{Season Number}E{Episode Number}**; and the following for Movies: **{Movie Name}({Movie Year})\{Movie Name}({Movie Year})**. Notice that the file extension is not included in the path.  Examples of files structured like this include: Game Of Thrones\Season 1\Game Of Thrones S01E03.mkv, Iron Man (2013)\Iron Man (2013).mkv

you can change this structure to whatever you desire.  At present the following variables can be used in movie paths: MovieName, ReleaseDate; and the following can be used in episode paths: SeriesName, SeasonNumber, EpisodeNumber, EpisodeName. These variables will be populated with data acquired using TVDB, TMDB and Open Subtitles.  You can parse these values before they are added to the path using various methods. These methods will need to be appended to the variable and can be chained.  for example {SeasonNumber}.Trim().LeftPad("2","0") will get the Season Number for your file from TVDB and will then trim and left pad the acquired value with 0s before adding the value to your path.  The Path Text boxes have Auto Complete on them so you will be prompted with appropriate variables and methods to use as you type.


# Features to be added before our first beta:

* would be nice to find a way to title case titles (i.e not just capitalise the first letter of each word but to not capitalise words like 'a' or 'it' etc)

* add move history to the db

* and undo move capability

* get subtitles    

* Ensure error is thrown if destination root and source paths specified in configs is not found during processing 

* validate paths based on environment. i.e linux vs windows etc. 

* upgrade logger to log4j2

* look into http://acoustid.org/ and http://musicbrainz.org/ for music identification?

* destination path rename? "naming format" or "naming and placement"?

* Configuration Wizard Screen 1 add a copy option as well as the move option in the Action dropdown. Useful for testing.

* Test this on Freenas with a Custom Jail Plugin

* Add log file viewer to web app component

********************************************************
Completed
********************************************************
* Add Media Service Selectors.  These will Use TVDB, TMDB and Open subtitles services to select files for movement and to populate variables used in the destination path.

* Need to provide more flexibility in how variables / file tokens are modified before being added to the path for eg in the file "game of thrones s1e2.mkv" we may extract the season as "1".  We may want to further pad the data with 0's on the left. something like fileBots method of chaining methods to variables like: {{name}}.Trim().Pad(0) etc could work?

* Need to capture file extension regadless of the file selection method(regex open subtitles etc...)

* add valid extensions field to configs

* allow for global paths i.e should only have to enter path to movie directory once 


* Bell & Whistle - add a option to select common regular expressions in the Advanced config page


* in the case where we cannot fine a match in open subtitles service using hash value we can search using movie name/ season info?