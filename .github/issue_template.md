(Describe your issue and goal here)

### Reproducible in:

```bash
mvn dependency:tree | grep com.slack.api
gradle dependencies | grep com.slack.api
java -version
sw_vers && uname -v # or `ver`
```

#### The Slack SDK version

(Paste the output of `mvn dependency:tree | grep com.slack.api` or `gradle dependencies | grep com.slack.api`)

#### Java Runtime version

(Paste the output of `java -version`)

#### OS info

(Paste the output of `sw_vers && uname -v` on macOS/Linux or `ver` on Windows OS)

#### Steps to reproduce:

(Share the commands to run, source code, and project settings (e.g., pom.xml/build.gradle))

1.
2.
3.

#### Expected result:

(Tell what you expected to happen)

#### Actual result:

(Tell what actually happened with logs, screenshots)

### Requirements

Please make sure if this topic is specific to this SDK. For general questions/issues about Slack API platform or its server-side, could you submit questions at https://my.slack.com/help/requests/new instead. :bow:

Please read the [Contributing guidelines](https://github.com/slackapi/java-slack-sdk/blob/main/.github/contributing.md) and [Code of Conduct](https://slackhq.github.io/code-of-conduct) before creating this issue or pull request. By submitting, you are agreeing to the those rules.
