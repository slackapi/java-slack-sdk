---
name: Question
about: Submit a question about this SDK
title: (Set a clear title describing your question)
labels: 'question'
assignees: ''
---

## Description

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

### Expected result:

(Tell what you expected to happen)

### Actual result:

(Tell what actually happened with logs, screenshots)

## Requirements (place an `x` in each of the `[ ]`)

(For general questions/issues about Slack API platform or its server-side, could you submit questions at https://my.slack.com/help/requests/new instead. :bow:)

* [ ] This is a question specific to this SDK project.
* [ ] I've read and understood the [Contributing guidelines](https://github.com/slackapi/java-slack-sdk/blob/main/.github/contributing.md) and have done my best effort to follow them.
* [ ] I've read and agree to the [Code of Conduct](https://slackhq.github.io/code-of-conduct).
* [ ] I've searched for any related issues and avoided creating a duplicate issue [here](https://github.com/slackapi/java-slack-sdk/issues).