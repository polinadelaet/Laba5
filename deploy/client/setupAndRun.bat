del /S /F /Q client-1.0-SNAPSHOT client-1.0-SNAPSHOT.tar client-1.0-SNAPSHOT.zip
cd ..\..\..\
call gradlew build
cd client\build\distributions\
"C:\Program Files\7-Zip\7zG" x client-1.0-SNAPSHOT.zip -y
cd client-1.0-SNAPSHOT\
java -jar lib\client-1.0-SNAPSHOT.jar