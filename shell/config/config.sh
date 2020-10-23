#!/bin/bash

bin=`dirname "$0"`
bin=`cd "${bin}";pwd`
config_path=${bin}/config
echo "shell path:$0, config file path: ${config_path}"
CONFIG_FILE=${config_path}/config.properties

KEY_BROKE_PARAM="broke.param"
KEY_TOPIC_PARAM="topic.param"
KEY_OFFSET_PARAM="offset.param"

readFromConfig() {
    local key=$1
    local defaultValue=$2
    local configFile=$3
    local value=`sed -n "s/^[ ]*${key}[ ]*=\([^#]*\).*$/\1/p" "${configFile}" | sed "s/^ *//;s/ *$//" | tail -n 1`
    [ -z "$value" ] && echo "$defaultValue" || echo "$value"
}

BROKE_PARAM=$(readFromConfig ${KEY_BROKE_PARAM} "" "${CONFIG_FILE}")
TOPIC_PARAM=$(readFromConfig ${KEY_TOPIC_PARAM} "" "${CONFIG_FILE}")
OFFSET_PARAM=$(readFromConfig ${KEY_OFFSET_PARAM} "" "${CONFIG_FILE}")
