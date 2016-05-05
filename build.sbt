// Project name (artifact name in Maven)
name := "KafkaStreaming"

// Organization name (e.g. the package name of the project)
organization := "org.myorganization.spark.streaming"

// Version
version := "1.0"

// Project description
description := "Read Kafka messages as streams"

// Do not append Scala versions to the generated artifacts
crossPaths := false

// Include Scala libraries into the dependency
autoScalaLibrary := true

// javac compiler options
javacOptions ++= Seq("-Xlint:unchecked")

// scalac compiler options
scalacOptions ++= Seq("-unchecked", "-deprecation")

// Compile first the Java code
compileOrder := CompileOrder.JavaThenScala

lazy val commonSettings = Seq(
  libraryDependencies += "org.apache.spark" % "spark-core_2.10"            % "1.6.1",
  libraryDependencies += "org.apache.spark" % "spark-streaming_2.10"       % "1.6.1",
  libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.1"
)


lazy val root = (project in file("."))
    .settings(
        commonSettings: _*
    )
    .settings(
        name := "KafkaStreaming",
        version := "1.0"
    )
