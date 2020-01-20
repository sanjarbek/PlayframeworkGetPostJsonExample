name := "PlayJsonPostGetExample"
 
version := "1.0" 
      
lazy val `playjsonpostgetexample` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "org.apache.poi" % "poi" % "4.1.0"
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "4.1.0"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

