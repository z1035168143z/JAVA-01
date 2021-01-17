@echo off

for %%I in (UseSerialGC,UseParallelGC,UseConcMarkSweepGC,UseG1GC) do (
	for %%J in (256m,512m,1g,2g,4g,8g,12g) do (java -DtimeInterval=10000 -Xms%%J -Xmx%%J -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+%%I -XX:-UseAdaptiveSizePolicy GCLogAnalysis > %%I-%%J.log)
)
pause