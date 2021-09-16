plugins {
  kotlin("multiplatform")
}

kotlin {
  js {
    browser {
      binaries.executable()
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("io.arrow-kt:arrow-fx-coroutines:1.0.0-SNAPSHOT")
      }
    }

    val jsMain by getting {
      dependsOn(commonMain)
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.5.0")
      }
    }
  }
}
