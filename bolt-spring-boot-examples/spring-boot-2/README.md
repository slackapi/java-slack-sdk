## Getting Started with Spring Boot 2

This README guides you on how to run your Bolt application code in a Spring Boot v3 application.

### Prerequisites

* Spring Boot 2 requires JDK 1.8 or newer
* This example app uses Gradle for building and running the app, but you can go with Maven too

### How to run

#### Simple App

When you run a simple app, which runs only for a single Slack workspace, you can install the app into the Slack workspace first and then set two environment variables as below:
```bash
export SLACK_BOT_TOKEN=(your token, which starts with xoxb-)
export SLACK_SIGNING_SECRET=(Settings > Basic Information > App Credentials > Signing Secret)
```

```bash
./gradlew bootRun
```

And then your app needs to expose a publicly accessible URL. [ngrok](https://ngrok.com/) is one of the easiest ways to serve such a URL with your local app.
Please note that the static subdomain feature is available for paid plan users.

```bash
ngrok http 3000 --subdomain your-own-domain
```

Now your `https://{public domain}/slack/events` should be a valid **Request URL** for your app.

#### OAuth Enabled App

To enable the OAuth flow, you need to configure your app at `https://api.slack.com/apps/{your app id}`,
plus the following env variables need to be set before running your app:

```bash
export SLACK_CLIENT_ID=(Settings > Basic Information > App Credentials > Client ID)
export SLACK_CLIENT_SECRET=(Settings > Basic Information > App Credentials > Client Secret)
export SLACK_SIGNING_SECRET=(Settings > Basic Information > App Credentials > Signing Secret)
```

With this setting, your app serves the following URLs:

* `https://{public domain}/slack/events` for the bi-communications with Slack API servers
* `https://{public domain}/slack/install`, `/slack/oauth_redirect` for the OAuth flow (web browser interactions with end-users)
