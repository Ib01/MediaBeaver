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

The only complex thing about this screen is the **Destination Path** field.  this field is critical as it defines the directory structure and name that will be used for the file when moving it. This field uses variables which you define. These variables must be wrapped in double braces {{SomeVaraible}}. you will assign values to these variables in subsequent steps. See image below for example

![generalInfo.png](https://bitbucket.org/repo/5MgKjp/images/1991323788-generalInfo.png)