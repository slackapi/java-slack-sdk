---
layout: ja
title: "基本的な Web API の利用"
lang: ja
---

# 基本的な Web API の利用

Slack SDK for Java の機能を使って、基本的な Slack API の呼び出しを実装してみましょう。

```java
import com.slack.api.Slack;
Slack slack = Slack.getInstance();
slack.methods(System.getenv("SLACK_BOT_TOKEN")).apiTest(r -> r.foo("bar"));
```
