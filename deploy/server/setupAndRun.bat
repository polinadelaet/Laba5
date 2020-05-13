del /S /F /Q Laba5-1.0-SNAPSHOT Laba5-1.0-SNAPSHOT.tar Laba5-1.0-SNAPSHOT.zip
cd ..\..\
call gradlew build
cd build\distributions\
"C:\Program Files\7-Zip\7zG" x Laba5-1.0-SNAPSHOT.zip -y
SET WC_FILE=./files/workerCollection
mkdir Laba5-1.0-SNAPSHOT\files
copy files\* Laba5-1.0-SNAPSHOT\files\
cd Laba5-1.0-SNAPSHOT\
java -jar lib\Laba5-1.0-SNAPSHOT.jar