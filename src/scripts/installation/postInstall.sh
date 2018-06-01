#!/bin/bash

set -x

USER=bahmni
GROUP=bahmni

id -g ${GROUP} 2>/dev/null
if [ $? -eq 1 ]; then
    groupadd ${GROUP}
fi

id ${USER} 2>/dev/null
if [ $? -eq 1 ]; then
    useradd -g ${USER} ${USER}
fi

usermod -s /usr/sbin/nologin bahmni

mkdir -p /opt/imis-connect/var/log/
mkdir /etc/imis-connect/
mkdir /var/log/imis-connect/

chown -R bahmni:bahmni /opt/imis-connect/
chmod +x /opt/imis-connect/bin/imis-connect

mv /opt/imis-connect/bin/imis-connect*.jar /opt/imis-connect/bin/imis-connect.jar

ln -sf /opt/imis-connect/etc/application.properties /etc/imis-connect/imis-connect.properties
ln -sf /opt/imis-connect/etc/log4j.properties /etc/imis-connect/log4j.properties
ln -sf /opt/imis-connect/var/log/imis-connect.log /var/log/imis-connect/imis-connect.log
ln -sf /opt/imis-connect/bin/imis-connect /etc/init.d/imis-connect

chkconfig --add imis-connect