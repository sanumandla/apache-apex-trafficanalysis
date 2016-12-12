# apache-apex-trafficanalysis
Traffic analysis application built using Apache Apex for DataTorrent hackathon

# Traffic analysis dataset can be found here 
https://github.com/caesar0301/awesome-public-datasets

# Command used to create a project with Apache Apex dependencies

mvn -B archetype:generate -DarchetypeGroupId=org.apache.apex -DarchetypeArtifactId=apex-app-archetype -DarchetypeVersion=3.3.0-incubating   -DgroupId=com.hackathon   -Dpackage=com.hackathon.trafficanalysis -DartifactId=trafficanalysis   -Dversion=1.0-SNAPSHOT
