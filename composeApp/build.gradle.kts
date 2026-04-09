plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        val webMain by creating {
            dependsOn(commonMain.get())
        }
        val webTest by creating {
            dependsOn(commonTest.get())
        }

        jsMain { dependsOn(webMain) }
        jsTest { dependsOn(webTest) }

        webMain.dependencies {
            implementation(compose.html.core)
            implementation(compose.runtime)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
