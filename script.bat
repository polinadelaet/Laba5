SET WC_FILE=./files/workerCollection
call gradlew jar
cd build\libs
java -jar Laba5-1.0-SNAPSHOT.jar
