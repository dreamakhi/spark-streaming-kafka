package org.myorganization.spark.streaming


import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.streaming.kafka._
import kafka.serializer.StringDecoder


/**
 * Read in Kafka messages as Spark Streams and process them.
 *
 */
object KafkaStreaming {
    def main(args: Array[String]): Unit = {
        val (batchDuration, topics, bootstrapServers) = getParams(args)

        val conf = new SparkConf().setAppName("gpKafkaStreaming")
        val sc   = new SparkContext(conf)
        val ssc  = new StreamingContext(sc, Seconds(batchDuration))

        val topicsSet   = topics.split(",").toSet
        val kafkaParams = Map[String, String]("bootstrap.servers" -> bootstrapServers, "auto.offset.reset" -> "smallest")
        val messages    = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

        val data                 = messages.map(_._2)
        val loggerSerializerLogs = data.map(_.split("""\s+"""))
                                       .filter(x => x.length > 6)
                                       .map(x => (x(0), x(6)))
                                       .filter(filterLogLines)
                                       .map(x => x._1)
        val logCounts            = loggerSerializerLogs.map(x => (x, 1L)).reduceByKey(_ + _)
        logCounts.print(10)

        ssc.start()
        ssc.awaitTermination()
    }


    def filterLogLines(line: Tuple2[String, String]): Boolean = {
        val pattern = """logger.+"""
        line._2.matches(pattern)
    }


    def getParams(args: Array[String]): Tuple3[Int, String, String] = {
        if (args.length !=3 ) {
            System.err.println(s"""
                |Usage: spark-kafka.sh <sampling-period> <topics> <bootstrap-servers>
                |  <sampling-period>    is the duration of each batch (in seconds)
                |  <topics>             is a list of one or more kafka topics to consume from
                |  <bootstrap-servers>  is a list of one or more Kafka bootstrap servers
                |
                """.stripMargin)
            System.exit(1)
        }
        Tuple3[Int, String, String](args(0).toInt, args(1), args(2))
    }
}
