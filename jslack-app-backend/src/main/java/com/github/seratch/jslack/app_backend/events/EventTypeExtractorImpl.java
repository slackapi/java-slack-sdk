package com.github.seratch.jslack.app_backend.events;

public class EventTypeExtractorImpl implements EventTypeExtractor {

    @Override
    public String extractEventType(String json) {
        StringBuilder sb = new StringBuilder();
        char[] chars = json.toCharArray();
        boolean isInsideEventData = false;
        for (int idx = 0; idx < (chars.length - 7); idx++) {
            if (!isInsideEventData && chars[idx] == '"'
                    && chars[idx + 1] == 'e'
                    && chars[idx + 2] == 'v'
                    && chars[idx + 3] == 'e'
                    && chars[idx + 4] == 'n'
                    && chars[idx + 5] == 't'
                    && chars[idx + 6] == '"'
                    && chars[idx + 7] == ':') {
                idx = idx + 8;
                isInsideEventData = true;
            }

            if (isInsideEventData && chars[idx] == '"'
                    && chars[idx + 1] == 't'
                    && chars[idx + 2] == 'y'
                    && chars[idx + 3] == 'p'
                    && chars[idx + 4] == 'e'
                    && chars[idx + 5] == '"'
                    && chars[idx + 6] == ':') {
                idx = idx + 7;
                int doubleQuoteCount = 0;
                boolean isPreviousCharEscape = false;
                while (doubleQuoteCount < 2 && idx < chars.length) {
                    char c = chars[idx];
                    if (c == '"' && !isPreviousCharEscape) {
                        doubleQuoteCount++;
                    } else {
                        if (doubleQuoteCount == 1) {
                            sb.append(c);
                        }
                    }
                    isPreviousCharEscape = c == '\\';
                    idx++;
                }
                break;
            }
        }
        return sb.toString();
    }

}
