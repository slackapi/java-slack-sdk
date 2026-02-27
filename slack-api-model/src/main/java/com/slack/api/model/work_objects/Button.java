package com.slack.api.model.work_objects;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.ThirdPartyAuthObject;
import com.slack.api.model.work_objects.external.ButtonProcessingState;
import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Predicates.instanceOf;

/**
 * Button Action Payload.
 * <p>
 * Represents the payload sent to the server from a `button` action.
 */
@Value
@Builder
public class Button extends PrimaryActions {
    @Required String blockId;
    @Required String actionId;
    @Required(validator = ButtonTypePredicate.class) String type;
    String style;
    @Required PlainTextObject text;
    String value;
    String url;
    String accessibilityLabel;
    ConfirmationDialogObject confirm;
    ThirdPartyAuthObject thirdPartyAuth;
    List<String> visibleToUserIds;
    ButtonProcessingState processingState;

    public static class ButtonTypePredicate implements FieldPredicate {
        @Override
        public boolean validate(Object obj) {
            return instanceOf(String.class).and(equalTo("button")).test(obj);
        }
    }
}
