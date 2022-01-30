#!/bin/bash
mvn compile
mvn install
cp -f target/omniverse-1.0-SNAPSHOT.jar ../Omniverse-Test/plugins/omniverse-1.0-SNAPSHOT.jar
tmux send-keys -t testserver.0 "reload confirm" ENTER