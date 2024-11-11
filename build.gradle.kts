plugins {
    id("java")
    id("antlr")
    id("application")
}

application {
    mainClass.set("org.example.Main")
}


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.antlr:antlr4:4.13.2")
    implementation("org.ow2.asm:asm:9.2")
    implementation("org.ow2.asm:asm-commons:9.2")
}

tasks.test {
    useJUnitPlatform()
}
