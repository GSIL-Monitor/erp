@echo off
for %%c in (admin deamon finance orders product purchase store tms) do ( if not exist d:\workspace\erp-v2\backend-servers\%%c-server\src\main\resources\logback-test.xml ( copy d:\workspace\erp-v2\tool\logback-test.xml d:\workspace\erp-v2\backend-servers\%%c-server\src\main\resources\   ) )
pause