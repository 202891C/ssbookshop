# ssbookshop


Command Prompt 1: (Start the MySQL server)
cd \myWebProject\mysql\bin
mysqld --console
Shutdown: Ctrl + C in the same terminal


Command Prompt 2: (Start a MySQL client. For creation and manipulation of database)
cd \myWebProject\mysql\bin
mysql -u myuser -p


Command Prompt 3: (Start/Restart the TomCat server)
cd \myWebProject\tomcat\bin 
startup  
Shutdown: Ctrl + C in the new TomCat terminal that appears on startup 


Command Prompt 4: (Compile the source code for new/updated Java files)
cd \myWebProject\tomcat\webapps\ssbookshop\WEB-INF\classes
javac -cp .;c:\myWebProject\tomcat\lib\servlet-api.jar _______.java


On Java file update:
1) Compile the source code for the Java file (create a .class file from the java file)
2) Restart the TomCat Server (Ctrl + C to close)