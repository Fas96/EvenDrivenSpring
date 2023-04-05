###  Event-driven  Spring boot, kafka and elastic

#### SER.1. How to Create Multi-Project Maven POMs
This is a simple example of how to use spring boot, kafka and elastic to create a simple event-driven application.

#### Objectives

Our objective is to create a  setup POM for our <b>microservices</b>. We will create a parent POM that will be used to manage the versions of the dependencies and plugins used in the microservices. 
We will also create a POM for each microservice that will inherit from the <b>parent POM.</b>

#### SER.1.1. Create a parent POM
Our <b>parent POM</b> does not contain any code. It is used to manage the <b>versions of the dependencies</b> and plugins used in the microservices.
So, It is a POM and not a <b>war</b> or <b>jar</b> package. Which implies we would use it as a base POM for our microservices.

```xml
	<packaging>pom</packaging>
```

<br/>

Our parent POM will inherit from the <b>spring-boot-starter-parent</b> POM. This POM contains the versions of the dependencies and plugins used in the spring boot applications.

```xml
 <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.0.5</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

<br/>

#### SER.1.2. Dependency Management
This helps define some base <b>dependencies</b> that will be used in the microservices. We can also define the <b>version</b> of the dependencies here.
```xml
<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter</artifactId>
                <version>${spring-boot.version}</version>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
				<scope>test</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
```

<br/>

#### SER.1.3. Build Management
Our Build Management contains a <pluginManagement> section. All the plugins and submodules can use the plugins defined in this section.
Without specifying versions.<b>Additionally, we define a maven compiler plugin</b> to use the <b>java.version</b> property defined in the properties section.
This is inherited by all the microservices. and the <source> and <target> of the compiler plugin will be set to the value of the <java.version> property.
<b>Note, after java 9, we can use the <release> tag to specify the java version.</b>

```xml
<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
                <version>${maven-compiler-plugin.version}</version>
				<configuration>
					<release> ${java.version} </release>
				</configuration>
			</plugin>
		</plugins>
		<pluginManagement>
			<plugins>
				<plugin>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-maven-plugin</artifactId>
					<version>${spring-boot.version}</version>
				</plugin>
			</plugins>
		</pluginManagement>
	</build>
```

#### SER.1.4. Properties
<b>Notice</b> the notion of <b>properties</b> in the parent POM. We can define properties in the parent POM and use them in the microservices.
It is a good practice to define the <b>versions</b> of the dependencies and plugins in the parent POM and use them in the microservices.
```xml
<properties>
    <java.version>17</java.version>
    <spring-boot.version>3.0.5</spring-boot.version>
    <maven-compiler-plugin.version>3.8.0</maven-compiler-plugin.version>
</properties>
```

#### SER.1.5. SUBMODULE CREATION (twitter-to-kafka-service)

If we click <New> and select <Module> from the context menu, we will see the <New Module> dialog. We can select the <Maven> module type and click <Next>.

<br/>

![This module gets data from twitter and puts them in topics of kafka](https://github.com/Fas96/T-images-repo/blob/main/1.submodule.png?raw=true "Submodule twitter-to-kafka-service")

<br/>

#####  WHAT OUR PARENT POM LOOKS LIKE NOW

Our parent module generates a <modules> tag to keep <b>submodules</b> inherating from it. We can add the <b>twitter-to-kafka-service</b> module to the <modules> tag.

```xml
<modules>
		<module>twitter-to-kafka-service</module>
</modules>
```

<br/>

#### SER.1.6. What our twitter-to-kafka-service(Submodule) POM looks like
It inherits from the <b>parent POM</b> and contains the <b>dependencies</b> and <b>plugins</b> used in the microservice.
Notice the parent of this submodule is our <b>parent POM</b> and not the <b>spring-boot-starter-parent</b> POM.
<br>
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project
        xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.microservices.code</groupId>
        <artifactId>microservices-start</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <artifactId>twitter-to-kafka-service</artifactId>
    <properties> 
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.twitter4j</groupId>
            <artifactId>twitter4j-stream</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

<br>

#### Summary
All subsequent microservices will look like the <b>twitter-to-kafka-service</b> module. The only difference is the <b>artifactId</b> and the <b>dependencies</b> and <b>plugins</b> used in the microservice.
<b>Notice</b> that we have not defined the <b>version</b> of the dependencies and plugins. We will use the <b>version</b> defined in the <b>parent POM</b> by default.
In the section above, our goal was to see how we can define a parent POM that will be used to manage the versions of the dependencies and plugins used in the microservices.
