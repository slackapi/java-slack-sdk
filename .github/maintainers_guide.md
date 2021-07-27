# Maintainers Guide

This document describes tools, tasks and workflow that one needs to be familiar with in order to effectively maintain this project. If you use this package within your own software but don't plan on modifying it, this guide is **not** for you.

## Tools

Maintaining this project requires installing [OpenJDK](https://openjdk.java.net/) in your development environment (for best results on macOS, use OpenJDK v11 via `brew install openjdk@11`). Also, [Apache Maven](https://maven.apache.org/) needs to be installed to build this project. All of the remaining tools are downloaded as `dependencies`, which means you'll have them available once you run `mvn test-compile` in a working copy of this repository.

## Tasks

### Testing

This project has tests for individual packages inside of each's respective `src/test/java` directory. All the tests under `test_locally` package are executed in every single GitHub Actions CI build as below.

```bash
./scripts/run_no_prep_tests.sh
./mvnw install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
./mvnw duplicate-finder:check
```

Apart from those, you need to run the rest on your local machine. `mvn test` command runs all the tests including the ones that requires access tokens to call Slack APIs in the **slack-api-client** project.

The tests under the `test_with_remote_apis` package requires some preparation.

* App(1): A Slack app for a workspace (Most tests work with free plan, some works only with any paid plan)
  * Add a bot user
  * Grant all the bot/user permissions
  * Enable Incoming Webhooks
  * Install the app to the development workspace
  * Create a shared channel with another workspace
* App(2): A Slack app for a workspace in a Grid (Enterprise Grid plan required)
  * Add a bot user
  * Grant all the user permissions
  * An admin of the workspace installs the app to the development workspace
* App(3): A Slack app installable to an Org (Enterprise Grid plan required)
  * Grant all the admin permissions
  * Implement the OAuth Flow and run it

#### Testing with any workspaces

By installing App(1), you get the followings. Set them as env variables.

* User token
* Bot token
* Incoming Webhooks

|Env Variable|Description|
|-|-|
|SLACK_SDK_TEST_USER_TOKEN|A user token with all the possible scopes for it. You can use a free workspace as a development workspace for the tests that require this token.|
|SLACK_SDK_TEST_BOT_TOKEN|A bot token with all the possible scopes for it. You can use a free workspace as a development workspace for the tests that require this token.|
|SLACK_SDK_TEST_INCOMING_WEBHOOK_URL|An incoming webhook issued by a Slack app.|
|SLACK_SDK_TEST_INCOMING_WEBHOOK_CHANNEL_NAME|The Slack channel name starting with # (say, #random) that the above webhook posts a message.|

#### Testing with any paid workspaces

By creating a shared channel in the workspace the App(1) has been installed, you get the channel ID of it.

|Env Variable|Description|
|-|-|
|SLACK_SDK_TEST_SHARED_CHANNEL_ID|An ID of a shared channel in the development workspace for App(1).|

#### Testing with Enterprise Grid

By installing App(2) and App(3), you get the followings. Set them as env variables.

* User token as an admin of the Org in Grid
* User token as an admin of a workspace in Grid

|Env Variable|Description|
|-|-|
|SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN|User token (`xoxp-` token) with a Grid admin permissions. To get this, the app needs to be installed to not a workspace but the Org in the Grid.|
|SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN|User token (`xoxp-` token) with a workspace admin permissions. The workspace must be one of the workspaces in an Enterprise Grid you run unit tests.|
|SLACK_SDK_TEST_GRID_WORKSPACE_BOT_TOKEN|User token (`xoxb-` token) with a workspace bot permissions. The workspace must be one of the workspaces in an Enterprise Grid you run unit tests.|
|SLACK_SDK_TEST_GRID_WORKSPACE_USER_TOKEN|User token (`xoxp-` token) with a workspace bot permissions. The workspace must be one of the workspaces in an Enterprise Grid you run unit tests.|
|SLACK_SDK_TEST_GRID_TEAM_ID|The `team_id` of the workspace `SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN` can manage.|
|SLACK_SDK_TEST_GRID_SHARED_CHANNEL_ID|A shared channel's ID `SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN` can manage.|
|SLACK_SDK_TEST_EMAIL_ADDRESS|An email address to invite.|

### Generating Documentation

The documentation is built using [Jekyll](https://jekyllrb.com/) and hosted with GitHub Pages. The source files are
contained in the `docs` directory. Reading the Jekyll configuration in `docs/_config.yml` is helpful to understand how
the documentation is organized and built.

Refer to the [README](https://github.com/slackapi/java-slack-sdk/blob/master/docs/README.md) for details.

### Releasing

#### Prerequisites

Place `$HOME/.m2/settings.xml` with your Sonatype account information.

```xml
<settings>
  <localRepository>/${your-home-dir}/.m2/repository</localRepository>
  <servers>
    <server>
      <username>${your-username}</username>
      <password>${your-password}</password>
      <id>sonatype-nexus-staging</id>
    </server>
    <server>
      <username>${your-username}</username>
      <password>${your-password}</password>
      <id>sonatype-nexus-snapshots</id>
    </server>
  </servers>
  <pluginGroups>
    <pluginGroup>org.apache.maven.plugins</pluginGroup>
    <pluginGroup>org.codehaus.mojo</pluginGroup>
  </pluginGroups>
</settings>
```

#### Operations

* Preparation
  * `git pull --rebase slackapi master`
  * Make sure there are no build failures at https://github.com/slackapi/java-slack-sdk/actions
* Set a new version
  * If you don't have `gnu-sed`, run `brew install gnu-sed` first
  * Run `scripts/set_version.sh (the version)` (e.g., `scripts/set_version.sh 1.0.0`)
* Ship the libraries
  * Run `scripts/release.sh` (it takes a bit long)
  * (If you encounter an error, log in https://oss.sonatype.org/ to check detailed information)
* Create GitHub Release(s) and add release notes
  * Prepare a release note by `git log --pretty=format:'%h %s by %an' --abbrev-commit | grep -v "Merge pull request " | head -50`
  * `git add . -v && git commit -m'version (your version here)'`
  * `git tag v(your version here)`
  * `git push slackapi --tags`
  * Open https://github.com/slackapi/java-slack-sdk/releases/new?tag=v${version}
* (Slack Internal) Communicate the release internally. Include a link to the GitHub Release(s).
* Announce on Bot Developer Hangout (`dev4slack.slack.com`) in **#slack-api**.
* (Slack Internal) Tweet? Not necessary for patch updates, might be needed for minor updates, definitely needed for
   major updates. Include a link to the GitHub Release(s).

## Workflow

### Versioning and Tags

This project is versioned using [Semantic Versioning](http://semver.org/). Each release is tagged using git. The naming
convention for tags is `v{version}`.

### Branches

`master` is where active development occurs. Long running named feature branches are occasionally created for
collaboration on a feature that has a large scope (because everyone cannot push commits to another person's open Pull
Request). After a major version increment, a maintenance branch for the older major version is left open (e.g. `v3`,
`v4`, etc)

### Issue Management

Labels are used to run issues through an organized workflow. Here are the basic definitions:

* `bug`: A confirmed bug report. A bug is considered confirmed when reproduction steps have been documented and the
  issue has been reproduced by a maintainer.
* `enhancement`: A feature request for something this package might not already do.
* `docs`: An issue that is purely about documentation work.
* `tests`: An issue that is purely about testing work.
* `needs feedback`: An issue that may have claimed to be a bug but was not reproducible, or was otherwise missing some
  information.
* `discussion`: An issue that is purely meant to hold a discussion. Typically the maintainers are looking for feedback
  in these issues.
* `question`: An issue that is like a support request where the user needed more information or their usage was not
  correct.
* `security`: An issue that has special consideration for security reasons.
* `good first contribution`: An issue that has a well-defined relatively-small scope, with clear expectations. It helps
  when the testing approach is also known.
* `duplicate`: An issue that is functionally the same as another issue. Apply this only if you've linked the other issue
  by number.
* `semver:major|minor|patch`: Metadata about how resolving this issue would affect the version number.

**Triage** is the process of taking new issues that aren't yet "seen" and marking them with a basic level of information
with labels. An issue should have **one** of the following labels applied: `bug`, `enhancement`, `question`,
`needs feedback`, `docs`, `tests`, or `discussion`.

Issues are closed when a resolution has been reached. If for any reason a closed issue seems relevant once again,
reopening is great and better than creating a duplicate issue.

## Everything else

When in doubt, find the other maintainers and ask.
