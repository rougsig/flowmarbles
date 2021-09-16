plugins {
  kotlin("multiplatform") version "1.5.21" apply false
}


allprojects {
  version = "1.0.0"
  group = "com.github.rougsig.flowmarbles"

  repositories {
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlinx")

    // for arrow-kt 1.0.0-SNAPSHOT
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
  }
}
