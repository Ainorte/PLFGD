# Setup

The first step is to clone the project.

```
git clone https://github.com/Ainorte/PLFGD.git
cd PLFGD
```

The repository is divided into two modules, each requiring a given tooling.

- `Android/`: Android project, requiring JDK 1.8, Gradle, and Android SDK 28
- `Java/`: Server/Common project, requiring JDK 1.11, and Maven

## `Java/`: Server

To set up and run the server, go inside the Java folder.

```
cd Java
```

The first step is to build the Maven project, which is done by the `install` Maven option.

```
mvn install
```

> The `install` operation is tasked with building Java source files into classes,
> but also packaging each module into a dedicated Jar file.
>
> This is required for the Server to find the common code, as it'll load the Common Jar file.

Once it is done, you can now move into the Server subfolder, to be able to start the server.
Starting the server is done through the `exec:java` Maven option.

```
cd Server
mvn exec:java
```

This should start the server without any issue.

> Be aware that compilation is done inside the `Java/` project folder,
> while execution is done inside the `Java/Server/` project folder.

## `Android/` 

To build the application, go inside the Android folder.

```
cd Android
```

To build the gradle project, enter the following command.

```
gradle build
```
