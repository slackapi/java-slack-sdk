package com.slack.api.methods.response.admin.analytics;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.slack.api.methods.SlackApiBinaryResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.util.json.GsonFactory;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

@Slf4j
@Data
public class AdminAnalyticsGetFileResponse implements SlackApiBinaryResponse {

    private boolean ok;
    private String error;
    private ResponseMetadata responseMetadata;

    /**
     * This method is almost completely unlike other Web API methods you encounter.
     * It doesn't return application/json with the traditional "ok": true response on success,
     * though you'll find "ok": false on failure.
     * <p>
     * Instead, it returns a single file, often very large,
     * containing JSON objects that are separated by newlines and then compressed with application/gzip.
     */
    private InputStream fileStream;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private byte[] loadedBytes = new byte[]{};

    @Override
    public byte[] asBytes() throws IOException {
        if (getFileStream() == null) {
            throw new IOException("The byte stream has been already consumed.");
        }
        synchronized (loadedBytes) {
            if (loadedBytes.length == 0) {
                InputStream is = getFileStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int readSize;
                while ((readSize = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, readSize);
                }
                buffer.flush();
                loadedBytes = buffer.toByteArray();
            }
            return loadedBytes;
        }
    }

    public void forEach(Consumer<AnalyticsData> handler) throws IOException {
        if (getFileStream() == null) {
            throw new IOException("The byte stream has been already consumed.");
        }
        Gson gson = GsonFactory.createSnakeCase();
        InputStream is = getFileStream();
        if (loadedBytes.length > 0) {
            // already the whole data is loaded in heap memory
            is = new ByteArrayInputStream(loadedBytes);
        }
        try (GZIPInputStream gis = new GZIPInputStream(is);
             InputStreamReader isr = new InputStreamReader(gis);
             BufferedReader br = new BufferedReader(isr)
        ) {
            String json;
            while ((json = br.readLine()) != null) {
                try {
                    AnalyticsData data = gson.fromJson(json, AnalyticsData.class);
                    handler.accept(data);
                } catch (JsonSyntaxException e) {
                    log.error("Failed to parse a row in analytics data - error: {}, data: {}",
                            e.getMessage(), json, e);
                }
            }
        } finally {
            // already consumed
            setFileStream(null);
        }
    }

    /**
     * Parsed Analytics Data
     */
    @Data
    public static class AnalyticsData {

        /**
         * The date this row of data is representative of
         * Example: 2020-09-13
         */
        private String date;

        /**
         * Unique ID of the involved Enterprise organization
         * Example: E2AB3A10F
         */
        private String enterpriseId;

        /**
         * The canonical, organization-wide user ID this row concerns
         * Example: W1F83A9F9
         */
        private String enterpriseUserId;

        /**
         * The email address of record for the same user
         * Example: person@acme.com
         */
        private String emailAddress;

        /**
         * This field is pulled from data synced via a SCIM API custom attribute
         * Example: 273849373
         */
        private String enterpriseEmployeeNumber;

        /**
         * User is classified as a guest (not a full workspace member) on the date in the API request
         * Example: false
         */
        private Boolean isGuest;

        /**
         * User is classified as a billable user (included in the bill) on the the date in the API request
         * Example: false
         */
        private Boolean isBillableSeat;

        /**
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request
         * Example: true
         */
        private Boolean isActive;

        /**
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request via the Slack iOS App
         * Example: true
         */
        private Boolean isActiveIos;

        /**
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request via the Slack Android App
         * Example: false
         */
        private Boolean isActiveAndroid;

        /**
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request via the Slack Desktop App
         * Example: true
         */
        private Boolean isActiveDesktop;

        /**
         * Total reactions added to any message type in any conversation type
         * by the user on the date in the API request. Removing reactions is not included.
         * This metric is not de-duplicated by message—if a user adds 3 different reactions
         * to a single message, we will report 3 reactions
         * Example: 20
         */
        private Integer reactionsAddedCount;

        /**
         * Total messages posted by the user on the date in the API request to all message and conversation types,
         * whether public, private, multi-person direct message, etc.
         * Example: 40
         */
        private Integer messagesPostedCount;

        /**
         * Total messages posted by the user in private channels and public channels on the date in the API request,
         * not including direct messages
         * Example: 30
         */
        private Integer channelMessagesPostedCount;

        /**
         * Total files uploaded by the user on the date in the API request
         * Example: 5
         */
        private Integer filesAddedCount;
    }
}