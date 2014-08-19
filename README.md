# Installation

1) Get the latest version of the source

2) Use Maven to run a **clean package** on the mediaBeaver project (the parent project)

3) place **\mediabeaver\MediaBeaver\MediaBeaverServer\target\mediaBeaverServer-0.0.1-SNAPSHOT.war** and **\mediabeaver\MediaBeaver\MediaBeaverServer\target\mediaBeaverCli-0.0.1-SNAPSHOT.jar** 
on the target system. 

4) go to the command line

5) navigate to the directory containing **mediaBeaverCli-0.0.1-SNAPSHOT.jar**

6) execute **java -jar mediaBeaverCli-0.0.1-SNAPSHOT.jar -initialise** to create the database and populate it with some default configuration.

7) navigate to the directory containing **mediaBeaverServer-0.0.1-SNAPSHOT.war**

8) execute **java -jar mediaBeaverServer-0.0.1-SNAPSHOT.war** (this will start the web app)

9) navigate to the web app: using **http;//{ip address of your host}:8081/configList**.  modify your configuration to suit (see below for assistance with this).

10) to move media on your server navigate to the directory containing **mediaBeaverServer-0.0.1-SNAPSHOT.war** and execute **java -jar mediaBeaverServer-0.0.1-SNAPSHOT.war -move**


# Getting started

1) from **http;//{ip address of your host}:8081/configList** select a configuration item to edit.  you should see the creen below.  

The only complex thing about this screen is the **Destination Path** field.  this field is critical as it defines the directory structure and name that will be used for the file when moving it. This field uses variables which you define. These variables must be wrapped in double braces like this: {{SomeVaraible}}. you will assign values to these variables in subsequent steps. See image below for example

![generalInfo.png](https://bitbucket.org/repo/5MgKjp/images/1991323788-generalInfo.png)

2) click on next and then select a regular expression selector to edit. you will see a screen like below.

The expression field contains the regular expression that will be used to both select a file for movement and to populate the variables used in its path. the expression must define groups (google regular expresion groups if you dont know what these are).

In the variable setters section you will assign groups captured by the regular expression to variables. group assembly will contain at least one group number surrounded by brackets.  You can optionaly define a replace regex and replace string to clean the variable before it is added to the path. Use the test expression section to test the expression works and that data is captured in to your variables as expected.

A file will only be moved if the regular expression selector gets data into all of the variables required by the path. Selectors will be processed in the order they are sorted in on the  **Add or Update Regular Expression Selectors** wizard step.

![regExSelector.png](https://bitbucket.org/repo/5MgKjp/images/1079213183-regExSelector.png)

3) after you have created / modified and sorted your regular expression selectors click Next to end the wizard and save your config item data

# Features to be added before our first beta:

* Ensure error is thrown if destination root and source paths specified in configs is not found during processing  

* upgrade logger to log4j2

* add move history to the db

* May need to provide more flexibility in how file tokens are modified before being added to the path for eg in the file "game of thrones s1e2.mkv" we 
may extract the season as "1".  We may want to further pad the data with 0's on the left.

* would be nice to find a way to title case titles (i.e not just capitalise the first letter of each word but to not capitalise words like 'a' or 'it' etc)  

* history of every move and undo capability

* Use TVDB, TMDB and Open subtitles to determine generate file names.

* get subtitles    

* allow for global paths i.e should  only have to enter path to movie directory once 

* look into http://acoustid.org/ and http://musicbrainz.org/ for music identification?