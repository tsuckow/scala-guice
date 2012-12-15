name := "Scala Guice"

organization := "net.codingwell"

version := "3.0.1-SNAPSHOT"

libraryDependencies += "com.google.inject" % "guice" % "3.0"

libraryDependencies += "com.google.inject.extensions" % "guice-multibindings" % "3.0"

libraryDependencies += "com.google.guava" % "guava" % "11.0.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

crossScalaVersions := Seq("2.8.2", "2.9.1", "2.9.2")

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

testListeners <<= target.map(t => Seq(new eu.henkelmann.sbt.JUnitXmlTestsListener(t.getAbsolutePath)))
