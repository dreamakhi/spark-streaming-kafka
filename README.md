# spark-streaming-kafka
Scala code to read text files distributed through Kafka as streams in Spark Streaming.

## Build
The `build.sbt` file sets various configuration parameters and lists the dependencies of the code to other components. 
To compile the code:
```
sbt clean compile
```
To create the jar file that is submitted to Spark:
```
sbt package
```
This will create `target/kafkastreaming-1.0.jar`.

## Submit to Spark
The Bash script `spark-kafka.sh` can be used to submit the code to Spark:
```
spark-kafka.sh target/kafkastreaming-1.0.jar <sampling-period>  <topics>  <bootstrap-servers>
```
The script collects the jar files that the Scala code depends on and submits them to Spark.

## Output
The `topics` specified in the command line are checked every `sampling-period` seconds for new 
messages.  The value part from the (key, value) pair is extracted and filtered according to
some criteria. Finally, a reduce operation is performed and the top 10 results are printed.
