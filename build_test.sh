#!/bin/bash
JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 mvn compile
JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 mvn install
# tell the server to kill the database ahead of time
tmux send-keys -t testserver.0 "debug_die" ENTER
sleep 0.5
# still using 1.0 for backwards compat
cp -f target/omniverse-1.1-SNAPSHOT.jar ../Omniverse-Test/plugins/omniverse-1.0-SNAPSHOT.jar
tmux send-keys -t testserver.0 "plugman reload omniverse" ENTER