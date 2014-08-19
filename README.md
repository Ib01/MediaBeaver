Installation 

1)Get the latest version of the source

2)Use Maven to run a clean package on the parent project (mediaBeaver)

3) place \mediabeaver\MediaBeaver\MediaBeaverServer\target\mediaBeaverServer-0.0.1-SNAPSHOT.war and \mediabeaver\MediaBeaver\MediaBeaverServer\target\mediaBeaverCli-0.0.1-SNAPSHOT.jar 
on the target system. 
4) go to the command line
5) navigate to the directory containing mediaBeaverCli-0.0.1-SNAPSHOT.jar
6) execute <b>java -jar mediaBeaverCli-0.0.1-SNAPSHOT.jar -initialise </b> to create the database and populate it with some default configuration.