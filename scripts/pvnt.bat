@echo off
@rem ###################################
@rem  ������ ��� ������� PVNT
@rem ###################################

set DIR=%~dp0
if "%DIR%" == "" set DIR=.\
set APPHOME=%DIR%..

set CLASSPATH=%APPHOME%\lib\pvnt-0.9.2.jar

@rem Execute Gradle
java.exe -classpath "%CLASSPATH%" kz.pvnhome.pvnt.PVNTemplateRunner %*