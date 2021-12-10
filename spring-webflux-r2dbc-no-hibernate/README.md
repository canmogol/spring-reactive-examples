# SpringBoot Webflux with R2DBC without Hibernate

Here are the steps to compile, native compile and docker image creation instructions.

**How to Compile Java**
```bash
mvn clean install
```

**How to Create Java Docker**
```bash
docker build . -t spring-webflux-r2dbc-no-hibernate
```

**How to Compile Native Executable**
You need GraalVM to compile to native application. One way to install GraalVM is to use SDKMan. You can install the SDK Man from its site https://sdkman.io/install
After that, you can install GraalVM with this command.
```bash
sdk install java 21.3.0.r17-grl
```

You can compile the native executable by running the following command.
```bash
mvn package -Pnative
```

**How to Create Native Docker**
```bash
docker build -f Dockerfile-native -t spring-webflux-r2dbc-no-hibernate-native .
```

