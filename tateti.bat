chcp 65001
reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f
dir /S /B src\*.java | findstr /V /C:"src\pruebas" > sources.txt
javac -d bin @sources.txt
cls
java -cp bin src/App.java
