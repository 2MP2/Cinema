# cinema
The the easiest way to set up this project is:\
1.Dowload Oracle 21c express edition and sql developer\
2.Create user cinema in sql developer\
Run this code as system user witch have priviliges or create new one e.g.:\
\
create user c##cinema identified by cinema;\
grant RESOURCE, CONNECT, dba to c##cinema;\
GRANT ALL PRIVILEGES TO c##cinema;\
\
3.Run sql file "cinema.sql" as cinem user \
4.Download this project to Intellij \
5.Add Oracle jdbc to this project \
6.If you run this code and get error "The Network Adapter could not establish the connection" it probably because line 18 in main need to be diffrent \
You need to change "jdbc:oracle:thin:@DESKTOP-NJJMCEP:1521:xe" to "jdbc:oracle:thin:@XXX:YYY:ZZZ", where XXX is your hostname or ip, YYY is port and ZZZ is SID. \
All this information you can find in your Oracle datebase "listener.ora" file.\

