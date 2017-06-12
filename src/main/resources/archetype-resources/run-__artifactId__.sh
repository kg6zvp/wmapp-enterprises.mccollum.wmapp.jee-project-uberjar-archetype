#!/bin/bash

###########################################################
#                                                         #
# Package this file in the same directory as your         #
# project's output (${artifactId}-swarm.jar) to run       #
# it, automatically creating a database file if necessary #
#                                                         #
###########################################################

java -jar ${artifactId}-swarm.jar -Dswarm.ds.connection.url=jdbc:h2:file:${artifactId}-database;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE
