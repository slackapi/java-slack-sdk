package com.slack.api.bolt.util;

public class ListenerCodeSuggestion {
    private ListenerCodeSuggestion() {
    }

    public static final String COMMON_PREFIX = "---\n" +
            "[Suggestion] You can handle this type of event with the following listener function:\n\n";

    public static final String WORKFLOW_STEP = COMMON_PREFIX +
            "WorkflowStep step = WorkflowStep.builder()\n" +
            "  .callbackId(\"copy_review\")\n" +
            "  .edit((req, ctx) -> { return ctx.ack(); })\n" +
            "  .save((req, ctx) -> { return ctx.ack(); })\n" +
            "  .execute((req, ctx) -> { return ctx.ack(); })\n" +
            "  .build();\n" +
            "\n" +
            "app.step(step);\n";

    public static String viewSubmission(String callbackId) {
        return COMMON_PREFIX +
                "app.viewSubmission(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  // Sent inputs: req.getPayload().getView().getState().getValues()\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String viewClosed(String callbackId) {
        return COMMON_PREFIX +
                "app.viewClosed(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String dialogSubmission(String callbackId) {
        return COMMON_PREFIX +
                "app.dialogSubmission(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String dialogSuggestion(String callbackId) {
        return COMMON_PREFIX +
                "app.dialogSubmission(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  List<Option> options = Arrays.asList(Option.builder().label(\"label\").value(\"value\").build());\n" +
                "  return ctx.ack(r -> r.options(options));\n" +
                "});\n";
    }

    public static String dialogCancellation(String callbackId) {
        return COMMON_PREFIX +
                "app.dialogCancellation(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String command(String command) {
        return COMMON_PREFIX +
                "app.command(\"" + command + "\", (req, ctx) -> {\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String attachmentAction(String callbackId) {
        return COMMON_PREFIX +
                "app.attachmentAction(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String blockAction(String actionId) {
        return COMMON_PREFIX +
                "app.blockAction(\"" + actionId + "\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String blockSuggestion(String actionId) {
        return COMMON_PREFIX +
                "app.blockSuggestion(\"" + actionId + "\", (req, ctx) -> {\n" +
                "  List<Option> options = Arrays.asList(Option.builder().text(plainText(\"label\")).value(\"v\").build());\n" +
                "  return ctx.ack(r -> r.options(options));\n" +
                "});\n";
    }

    public static String event(String eventTypeAndSubtype) {
        String className = toEventClassName(eventTypeAndSubtype);
        return COMMON_PREFIX +
                "app.event(" + className + ".class, (payload, ctx) -> {\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String toEventClassName(String eventTypeAndSubtype) {
        String eventType = eventTypeAndSubtype;
        String[] elements = eventTypeAndSubtype.split(":");
        if (elements.length == 2) {
            // message events with subtype
            eventType = elements[0] + "_" + elements[1]
                    .replaceFirst("_message", "")
                    .replaceFirst("message_", "");
        }
        StringBuilder sb = new StringBuilder();
        char[] cs = eventType.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (i == 0) {
                sb.append(Character.toUpperCase(c));
            } else if (c == '_') {
                i++;
                sb.append(Character.toUpperCase(cs[i]));
            } else {
                sb.append(c);
            }
        }
        return sb + "Event";
    }

    public static String globalShortcut(String callbackId) {
        return COMMON_PREFIX +
                "app.globalShortcut(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }

    public static String messageShortcut(String callbackId) {
        return COMMON_PREFIX +
                "app.messageShortcut(\"" + callbackId + "\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
    }
}
