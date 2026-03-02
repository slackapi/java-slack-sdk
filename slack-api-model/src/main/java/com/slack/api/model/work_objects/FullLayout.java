package com.slack.api.model.work_objects;

import com.slack.api.model.block.InputBlock;
import com.slack.api.model.work_objects.Title;
import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.FieldPredicate;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.google.common.base.Predicates.instanceOf;
import static java.util.function.Predicate.isEqual;

@Value
@Builder
public class FullLayout {
    public static final String LAYOUT_TYPE = "full";
    @Required(validator = FullLayoutTypePredicate.class) String layoutType;
    Title headerTitle;
    Title headerSubtitle;
    /**
     * A title field on an external work object.
     */
    @Required FullLayout.Title title;
    Subtitle subtitle;
    /**
     * When true, at least one of the fields in this schema can be edited by the user.
     */
    Boolean editable;
    Fields fields;
    Actions actions;

    @Value
    @Builder
    public static class Title {
        /**
         * Plan text fallback of the field value.
         */
        @Required String text;
        InputBlock input;
    }

    @Value
    @Builder
    public static class Actions {
        @Required List<PrimaryActions> primaryActions;
        List<ActionMenu> primaryActionsMenu;
        @Required OverflowActions overflowActions;
        @Required ActionBlockPayload blockPayload;
    }

    public static class FullLayoutTypePredicate implements FieldPredicate {
        @Override
        public boolean validate(Object obj) {
            return instanceOf(String.class).and(isEqual(LAYOUT_TYPE)).test(obj);
        }
    }
}
