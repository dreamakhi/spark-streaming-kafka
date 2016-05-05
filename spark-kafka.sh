#!/bin/bash
#
# Submits the scala script to Apache Spark

export LD_LIBRARY_PATH=${HADOOP_HOME}/lib/native:${LD_LIBRARY_PATH}

JARS=""
JARS+="${HOME}/.ivy2/cache/org.apache.spark/spark-streaming-kafka_2.10/jars/spark-streaming-kafka_2.10-1.6.1.jar"

MASTER="yarn"
MODE="client"


spark-submit --master "${MASTER}" --deploy-mode "${MODE}" --jars "${JARS}"  "$@"
