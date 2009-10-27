@echo off
setlocal enableextensions enabledelayedexpansion

set cpVars=lib\selenium-server-1.0.1.jar;lib\selenium-grid-remote-control-standalone-ITMill_TestBench.jar

set HOST=192.168.56.1
set HUBURL=http://192.168.56.1:4444
set ENVIRONMENT=*iexplore,*opera,*safari,*firefox,*googlechrome,*chrome
set PORT=5555
set USEREXTENSIONS=user-extensions.js

if not x%1x==xx set HOST=%1
if not x%2x==xx set HUBURL=%2
if not x%3x==xx set ENVIRONMENT=%3
if not x%4x==xx set PORT=%4
if not x%5x==xx set USEREXTENSIONS=%5


java -cp "%cpVars%" com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringRemoteControlLauncher -host %HOST% -port %PORT% -hubUrl %HUBURL% -env %ENVIRONMENT% -userExtensions %USEREXTENSIONS%

endlocal