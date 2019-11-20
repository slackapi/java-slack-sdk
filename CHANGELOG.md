# jSlack Release Notes

## version 3.1.2 (2019-11-20)

* c5c3393 Add App#status() and allow skipping calling App#start() method by Kazuhiro Sera
* 588904d Fix View object to be compatible with App Home by Kazuhiro Sera

## version 3.1.1 (2019-11-19)

* 443ab0d Fix a potential parse error with some Events API payloads by Kazuhiro Sera
* ce89db8 Add Spring Boot examples by Kazuhiro Sera
* 436caa3 Micronaut support (#274) by Kazuhiro Sera
* 4a327cd Fix deprecation warnings in tests by Kazuhiro Sera
* 5253798 Fix Quarkus example by Kazuhiro Sera
* ed840bf Add Quarkus Docker image example by Kazuhiro Sera
* 0ad83f1 Add Quarkus app examples by Kazuhiro Sera
* 16e0ef5 Bump the versions of okhttp, gson, lombok, slf4j-api by Kazuhiro Sera

## version 3.1.0 (2019-11-15)

* 40bd9c0 Fix #267 Wrong "metadata" property type in the jslack.api.model.Attachment by Kazuhiro Sera
* f3f9688 Support rich_text_list, rich_text_quote, rich_text_preformatted #266 #268 by Kazuhiro Sera
* c7647a5 Fix #264 Unknown RichTextSectionElement type: broadcast by Kazuhiro Sera

### Incompatibility

To support more `rich_text` block elements, we had to introduce some breaking changes to the JSON parser by https://github.com/seratch/jslack/pull/269

* Rename `GsonRichTextSectionElementFactory` to `GsonRichTextElementFactory`
* Extract `RichTextSectionElement.Element` as `RichTextElement`

`Attachment#metadata` used to be string type but it was wrong. https://github.com/seratch/jslack/pull/270 changed the field to be correct.

## version 3.0.5 (2019-11-14)

* a52cff8 Update README by Kazuhiro Sera
* b714317 Add admin.inviteRequests.* APIs by Kazuhiro Sera
* 3f150fd Fix #260 Enable jSlack users to configure API url prefix (#262) by Kazuhiro Sera

## version 3.0.4 (2019-11-12)

* 82d7a1a Add enterprise_id in event request context by Kazuhiro Sera
* 5d06fe6 Ensure team and enterprise ids are set on requests by Joel McCance

## version 3.0.3 (2019-11-09)

* 55cc2c5 Fix postAsBot to actually post as bot, not as user by Antonio Tomac
* f54633c Add new fields in Audit Logs API by Kazuhiro Sera
* d34a372 Fix #254 Lightning UrlVerification fails signature validation by Joel McCance

## version 3.0.2 (2019-11-06)

* 6262137 Add bot_link to latest field by Kazuhiro Sera
* b510128 Fix #249 Incorrect BlockActionPayload definition (#253) by Kazuhiro Sera
* b7ca3d8 Add missing types of rich sections (#251) by Kostiantyn Severynov

## version 3.0.1 (2019-10-26)

* 356b5b0 Lightning: Add ssl_check request handler by Kazuhiro Sera

## version 3.0.0 (2019-10-26)

* 69c5589 Add admin.* apis added in Oct 2019 by Kazuhiro Sera
* 090da30 Add App Home support by Kazuhiro Sera
* c98befc Add Granular Bot Permissions support by Kazuhiro Sera
* de175cb Add a GitHub Action to build Docker images by Kazuhiro Sera
* 6b8a9b6 Add Docker examples (Amazon ECS, Google Cloud Run) by Kazuhiro Sera
* 8851614 Add user.is_workflow_bot in response by Kazuhiro Sera
* 9a71bc8 Add historical data support and incoming webhooks in InstallationService #234 by Kazuhiro Sera
* 382c311 Fix #235 MultiTeamsAuthorization fails due to missing team_id / enterprise_id in ActionContext by Kazuhiro Sera
* 395a09a Mark workspace app related properties as deprecated by Kazuhiro Sera
* 638d43a Fix #230 selectedUsers in ViewState is missing by Kazuhiro Sera
* 7c783b3 Add bot_profile etc (added recently) by Kazuhiro Sera
* eaf6ced Fix dnd.teamInfo has been broken by Kazuhiro Sera
* f6b1044 Add Lightning README by Kazuhiro Sera
* 995c587 Add OAuth flow support in Lightning by Kazuhiro Sera
* 162dab1 Fix oauth.access API to use basic auth by Kazuhiro Sera
* 783507b Add Lightning, new module to build Slack apps quickly ⚡ by Kazuhiro Sera
* e5deef6 Apply a bunch of improvements for Slack app dev by Kazuhiro Sera
* 773a699 Add include_num_members to conversations.info params by Kazuhiro Sera

### Topics

#### Lightning to build Slack apps easily!

Check [the readme](https://github.com/seratch/jslack/tree/master/jslack-lightning) and [Kotlin examples](https://github.com/seratch/jslack/tree/master/jslack-lightning-kotlin-examples/src/main/kotlin/examples)!

### Incompatibility

The following classes are no longer internal classes. They are in the same package as independent ones.
 
* DialogSuggestionResponse.Option
* DialogSuggestionResponse.OptionGroup
* BlockSuggestionResponse.Option
* BlockSuggestionResponse.OptionGroup

## version 2.2.3 (2019-10-02)

* c27dbd1 #224 Add block_suggestion support by Kazuhiro Sera
* 4902d12 Change the type in view payloads to be immutable by Kazuhiro Sera

## version 2.2.2 (2019-10-01)

* f6b2cde #223 Add view payload JSON samples by Kazuhiro Sera
* 659abe9 #223 Add multi select blocks & fix input block errors by Kazuhiro Sera

## version 2.2.1 (2019-10-01)

* 08ba6d4 Add view to block_actions payload (ref #217) by Kazuhiro Sera
* fe4f4d6 Update search results to be compatible with the latest test results by Kazuhiro Sera
* f57235e Add a bunch of pref actions newly added in Audit Logs API by Kazuhiro Sera
* 565f4a6 Fix #220 rich_text blocks support by Kazuhiro Sera

## version 2.2.0 (2019-09-28)

* ebcd160 #217 Add Block Kit in Modals support by Kazuhiro Sera
* e867f4e Fix #218 by adding missing properties in event payloads by Kazuhiro Sera
* a7448d9 Fix #128 chat.update test issues by Kazuhiro Sera

## version 2.1.4 (2019-09-12)

* 7fce724 Fix #214 by providing EventTypeExtractor interface by Kazuhiro Sera

## version 2.1.3 (2019-09-05)

* ff74841 subtype property for the app_mention event (#213) by Subrahmanyam
* 98e6dd0 Add shared-channel related attributes to conversation by Kazuhiro Sera

## version 2.1.2 (2019-08-28)

* 1be4469 Add status API client by Kazuhiro Sera
* 9c26259 Add pref.ent_required_browser_name to known audit actions by Kazuhiro Sera

## version 2.1.1 (2019-08-24)

* 7bc8b9d Change the types of user.enterprise_user.is_admin, is_owner (string -> boolean) by Kazuhiro Sera

`user.enterprise_user.is_admin`, `user.enterprise_user.is_owner` are added in 2.1.0. 
We assume no one uses the version as it was released on the same day. 
But, if you had to deal with the incompatibility in your projects, we're sorry for that inconvenience.

## version 2.1.0 (2019-08-24)

* 5034343 Add admin.apps APIs to the library by Kazuhiro Sera
* 3962f7b Fix #208 conversation.is_moved by Kazuhiro Sera

### Incompatibility

The following two classes have been re-packaged.

* `com.github.seratch.jslack.api.methods.request.admin.AdminUsersSessionResetRequest` -> `com.github.seratch.jslack.api.methods.request.admin.users.AdminUsersSessionResetRequest`
* `com.github.seratch.jslack.api.methods.response.admin.AdminUsersSessionResetResponse` -> `com.github.seratch.jslack.api.methods.response.admin.users.AdminUsersSessionResetResponse`

## version 2.0.0 (2019-08-17)

* 93f8146 Bump okhttp version to 4.1.0 - fixes #179 by Kazuhiro Sera
* 4b98417 Add a constants for Audit Logs Actions by Kazuhiro Sera
* e1cb75d Improve SCIM, Audit API clients by Kazuhiro Sera
* 5d39d92 Remove starchart due to its unstability by Kazuhiro Sera
* 99b41f1 Rename blocksText to blocksAsString + support similar methods too by Kazuhiro Sera
* a430097 modified the ChatPostMessage to set blocks as string by Subbu

## Memo

```
git log --pretty=format:'%h %s by %an' --abbrev-commit | grep -v "Merge pull request " | head -50
```
