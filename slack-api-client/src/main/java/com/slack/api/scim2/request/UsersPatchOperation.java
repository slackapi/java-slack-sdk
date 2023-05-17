package com.slack.api.scim2.request;

import com.slack.api.scim2.model.PatchOperation;
import com.slack.api.scim2.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersPatchOperation {
    private PatchOperation op;
    private String path;

    private String stringValue;
    // only when the "path" is about "emails"
    private User.Email emailValue;
    private List<User.Email> emailValues;
    // only when the "path" is about "phoneNumbers"
    private User.PhoneNumber phoneNumberValue;
    private List<User.PhoneNumber> phoneNumberValues;
    // only when the "path" is about "photos"
    private User.Photo photoValue;
    private List<User.Photo> photoValues;
    // only when the "path" is about "addresses"
    private User.Address addressValue;
    private List<User.Address> addressValues;
    // only when the "path" is about "roles"
    private User.Role roleValue;
    private List<User.Role> roleValues;
    // only when the "path" is about "name"
    private User.Name nameValue;
    // only when the "path" is about "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    private User.Extension extensionValue;
    // only when the "path" is about "urn:ietf:params:scim:schemas:extension:slack:guest:2.0:User"
    private User.SlackGuest slackGuestValue;

    @Data
    @Builder
    public static class Serializable {
        private PatchOperation op;
        private String path;
        private Object value;
    }

    public Serializable toSerializable() {
        return Serializable.builder()
                .op(this.getOp())
                .path(this.getPath())
                .value(this.extractValue())
                .build();
    }

    private Object extractValue() {
        if (this.getStringValue() != null) {
            return this.getStringValue();
        } else if (this.getEmailValue() != null) {
            return this.getEmailValue();
        } else if (this.getEmailValues() != null) {
            return this.getEmailValues();
        } else if (this.getPhoneNumberValue() != null) {
            return this.getPhoneNumberValue();
        } else if (this.getPhoneNumberValues() != null) {
            return this.getPhoneNumberValues();
        } else if (this.getPhotoValue() != null) {
            return this.getPhotoValue();
        } else if (this.getPhotoValues() != null) {
            return this.getPhotoValues();
        } else if (this.getAddressValue() != null) {
            return this.getAddressValue();
        } else if (this.getAddressValues() != null) {
            return this.getAddressValues();
        } else if (this.getRoleValue() != null) {
            return this.getRoleValue();
        } else if (this.getRoleValues() != null) {
            return this.getRoleValues();
        } else if (this.getNameValue() != null) {
            return this.getNameValue();
        } else if (this.getExtensionValue() != null) {
            return this.getExtensionValue();
        } else if (this.getSlackGuestValue() != null) {
            return this.getSlackGuestValue();
        } else {
            // "remove" operation does not require any value
            return null;
        }
    }
}
