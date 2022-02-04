## Quarkus for Slack apps!

Quarkus is a fantastic project which allow Java developers to be more productive and deploy pretty much smaller binaries to production servers!

https://quarkus.io/

## How to run on your local machine

```bash
git clone git@github.com:slackapi/java-slack-sdk.git
cd java-slack-sdk/
mvn install -Dmaven.test.skip=true
mvn -pl bolt-quarkus-examples compile quarkus:dev
ngrok http 3000
```

## How to build

```bash
mvn -pl bolt-quarkus-examples package
java -jar bolt-quarkus-examples/target/bolt-quarkus-examples-*-runner.jar
```