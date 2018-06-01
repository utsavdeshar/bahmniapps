#!/bin/bash

set -x

service imis-connect stop || true

rm -rf /opt/imis-connect/
rm -f /etc/init.d/imis-connect
rm -rf /etc/imis-connect/
rm -rf /var/log/imis-connect/