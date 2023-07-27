echo port number %1
FOR /F "tokens=5" %%T IN ('netstat -a -n -o ^| findstr %1 ') DO (
				SET /A ProcessId=%%T) &GOTO SkipLine                                                   
				:SkipLine                                                                              
				echo ProcessId to kill = %ProcessId%
				taskkill /f /pid %ProcessId%