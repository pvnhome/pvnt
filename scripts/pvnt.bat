@echo off
@rem ###################################
@rem  PVNT startup script for Windows
@rem ###################################

set DIR=%~dp0
if "%DIR%" == "" set DIR=.\
set APPHOME=%DIR%..

set CLASSPATH=%APPHOME%\lib\pvnt-0.9.2.jar

@rem Execute PVNT
java.exe -classpath "%CLASSPATH%" kz.pvnhome.pvnt.PVNTemplateRunner %*
