## Quarkus for Slack apps!

Quarkus is a fantastic project which allow Java developers to be more productive and deploy pretty much smaller binaries to production servers!

https://quarkus.io/

## How to run on your local machine

```bash
mvn compile quarkus:dev
ngrok http 8080
```

## How to build

```bash
mvn package
java target/bolt-quarkus-examples-3.1.0-runner.jar # listens 8080 port
```