# Security Policy

Slack takes the security of its software and services seriously, including all open-source repositories managed through the [slackapi](https://github.com/slackapi) GitHub organization.

## Reporting a Vulnerability

**Do NOT report security vulnerabilities through public GitHub issues, pull requests, or discussions.**

If you believe you have found a security vulnerability in `java-slack-sdk`, please report it through the Slack bug bounty program on HackerOne:

**<https://hackerone.com/slack>**

Even if `java-slack-sdk` is not explicitly listed as an in-scope asset on the HackerOne program page, reports for vulnerabilities in this package should still be submitted there. The Slack security team triages reports for all `slackapi` open-source repositories through this program.

If HackerOne is inaccessible, you may alternatively report the issue to [security@salesforce.com](mailto:security@salesforce.com).

Please do not discuss potential vulnerabilities in public without first coordinating with the security team.

## What to Include

To help us triage and respond quickly, please include:

- Type of vulnerability (e.g., signature bypass, token leakage, denial of service)
- Affected version(s) of `java-slack-sdk`
- Step-by-step reproduction instructions
- Proof-of-concept code or payloads, if available
- Impact assessment: what an attacker could achieve
- Any specific configuration required to trigger the vulnerability
- Affected source file paths, if known

## Threat Model

The Slack SDK for Java includes both Bolt for Java (an app framework) and low-level API clients. Its security boundary covers the integrity and confidentiality of the interface between the Slack platform and developer application code.

### In Scope

The following are considered SDK/framework vulnerabilities:

- Bypass of request signature verification (HMAC-SHA256 validation)
- OAuth token leakage or cross-tenant token exposure during authorization flows
- Token rotation bypass or refresh token mishandling
- Denial of service caused by malformed or specially crafted payloads processed by framework internals
- Authentication or authorization bypass in any built-in adapter (Servlet, Jetty, Socket Mode, AWS Lambda, Google Cloud Functions)
- Information disclosure through framework error responses or timing side channels
- Header injection via token values in HTTP requests
- Bypass of the `ssl_check` endpoint protections

### Out of Scope

The following are NOT SDK/framework vulnerabilities:

- Vulnerabilities in the JVM runtime, operating system, or hosting infrastructure
- Security issues in developer application logic built on top of the SDK (e.g., SQL injection caused by passing unsanitized payload data to a database)
- Vulnerabilities in third-party Maven dependencies chosen and installed by the developer outside of the SDK's direct dependencies
- Vulnerabilities in Slack's server-side platform infrastructure (report those directly under Slack's main HackerOne scope)
- Attacks that require possession of a valid signing secret or bot token
- Issues that only affect end-of-life versions with no reproduction on supported versions

## Disclosure Policy

This project follows coordinated disclosure:

- Allow a reasonable timeframe for the team to investigate, develop, and release a fix before any public disclosure.
- Researchers who follow responsible disclosure practices are eligible for recognition and bounty consideration through the Slack HackerOne program.
