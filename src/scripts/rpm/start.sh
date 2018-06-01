#!/bin/bash

nohup java -jar /opt/imis-connect/bin/imis-connect.jar \
 --spring.config.location=/etc/imis-connect/imis-connect.properties \
 >> /var/log/imis-connect/imis-connect.log \
 2>&1 &