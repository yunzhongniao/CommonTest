#!/bin/bash

BIN_PATH=`dirname "$0"`
BIN_PATH=`cd "${BIN_PATH}";pwd`
source "${BIN_PATH}/config.sh"

usage() {
    echo "param error!"
    echo "usage: start.sh [-b broke] [-t topic] [-o offset]"
}

while getopts 'b:t:o:' OPT
do
    case $OPT in
        b) BROKE_PARAM=${OPTARG};;
        t) TOPIC_PARAM=${OPTARG};;
        o) OFFSET_PARAM=${OPTARG};;
        ?) echo "unknow param :${OPTARG}";;
    esac
done

if [ -z $BROKE_PARAM ]; then
    BROKE_PARAM=${BROKE_CONIG}
fi
if [ -z $TOPIC_PARAM ]; then
    TOPIC_PARAM=${TOPIC_CONIG}
fi
if [ -z $OFFSET_PARAM ]; then
    OFFSET_PARAM=${OFFSET_CONIG}
fi


