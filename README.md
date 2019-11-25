# YALMON-Agent
Yet Another Linux MONitor Agent.

### Built With

- [Spring Boot](https://start.spring.io)
- [Checkstyle](http://checkstyle.sourceforge.net/)
- [Findbugs](http://findbugs.sourceforge.net/)
- [Lombok](https://projectlombok.org/)
- [Maven](https://maven.apache.org/)
- [Java8](https://openjdk.java.net/install/)

- [Travis](https://travis-ci.org/) [![Build Status](https://travis-ci.org/canmogol/yalmon-agent.svg?branch=master)](https://travis-ci.org/canmogol/yalmon-agent)
- [SonarQube](https://sonarcloud.io/dashboard?id=com.yalmon%3Ayalmon-agent) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.yalmon%3Ayalmon-agent&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.yalmon%3Ayalmon-agent)
- [Mergify](https://mergify.io) [![Mergify Status](https://img.shields.io/endpoint.svg?url=https://gh.mergify.io/badges/canmogol/yalmon-agent&style=flat)](https://mergify.io)
- [Better Code Hub](https://bettercodehub.com) [![BCH compliance](https://bettercodehub.com/edge/badge/canmogol/yalmon-agent?branch=master)](https://bettercodehub.com/)

### Getting Started

You can compile the project only by running the `mvnw` command line script.

On *nix environment you may run the `mvnw` shell script,
```bash
./mvnw clean install
```

On Windows environment you may run the `mvnw.cmd` command.
```bash
mvnw.cmd clean install
```

### Installation

By running `clean install` gaol, maven will install the artifact to the .m2 folder
under the home directory. You will also find the executable `Jar` file under the
`target` folder which you may run with the `java -jar JarFileName.jar` command.

### Native Image Compilation

You need GraalVM to compile JAR file to native code, 
an easy way to install GraalVM is to use `sdk` tool.

```bash
# installs sdk command line tool  
curl -s "https://get.sdkman.io" | bash
```

You can install and use GraalVM using `sdk` tool.

```bash
# find all the available Java versions
sdk list java
# chose a GraalVM version, such as 19.2.1-grl
# should download and install GraalVM
sdk install java 19.2.1-grl
# switch to GraalVM Java
sdk use java 19.2.1-grl
# should install "Native Image" component
gu install native-image
```

You can build the native executable using maven 'graal' profile.

```bash
# maven should build the graal profile which is  
mvn clean package -Pgraal
```

Executable will be under the `target` folder with the `yalmon` name, 
you can run the executable just like any executable. 

```bash
# within the target folder
./yalmon
```

### Contributing
Contributions are what make the open source community such an amazing place to be learn,
inspire, and create. Any contributions you make are **greatly appreciated**.

### Fork the Project
Create your Feature Branch (git checkout -b feature/AmazingFeature)
Commit your Changes (git commit -m 'Add some AmazingFeature)
Push to the Branch (git push origin feature/AmazingFeature)
Open a Pull Request

##### License
Distributed under the MIT License. See `LICENSE` for more information.

