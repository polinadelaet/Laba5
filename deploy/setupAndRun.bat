del /S /F /Q Laba5-1.0-SNAPSHOT Laba-1.0-SHAPSHOT.tar Laba5-1.0-SHAPSHOT.zip
cd ..\..\
call gradlew build
cd build\distributions\
"C:\Program Files\7-Zip\7zG" x Laba5-1.0-SNAPSHOT.zip -y
copy "bin\*" "Laba5-1.0-SNAPSHOT\bin\"
SET WC_FILE=./files/workerCollection
call Laba5-1.0-SNAPSHOT\bin\Laba5.bat
