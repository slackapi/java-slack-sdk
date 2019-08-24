# jSlack Release Notes

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
