#!/bin/bash

DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,address=8030,server=y,suspend=n"

nohup java -jar ${DEBUG_OPTS} /opt/imis-connect/bin/imis-connect.jar \
 --spring.config.location=/etc/imis-connect/imis-connect.properties \
 >> /var/log/imis-connect/imis-connect.log \
 2>&1 &