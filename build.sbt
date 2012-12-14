name := "Scala Guice"

organization := "uk.me.lings"

version := "3.0.1-SNAPSHOT"

libraryDependencies += "com.google.inject" % "guice" % "3.0"

libraryDependencies += "com.google.inject.extensions" % "guice-multibindings" % "3.0"

libraryDependencies += "com.google.guava" % "guava" % "11.0.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

crossScalaVersions := Seq("2.8.2", "2.9.2")

publishTo := Some(Resolver.file("file",  new File("repo")))

testListeners <<= target.map(t => Seq(new eu.henkelmann.sbt.JUnitXmlTestsListener(t.getAbsolutePath)))
