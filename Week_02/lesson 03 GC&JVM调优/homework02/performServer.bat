@echo off

for %%I in (UseSerialGC,UseParallelGC,UseConcMarkSweepGC,UseG1GC) do (
	for %%J in (256m,512m,1g,2g,4g) do (
		START "gateway" java -Xms%%J -Xmx%%J -XX:+PrintGCDetails -Xloggc:server-%%I-%%J.log -XX:+%%I -XX:-UseAdaptiveSizePolicy -jar gateway-server-0.0.1-SNAPSHOT.jar
	    sb -u http://localhost:8088/api/hello -c 20 -N 20 > server-sb-%%I-%%J.log
    )
)
pause