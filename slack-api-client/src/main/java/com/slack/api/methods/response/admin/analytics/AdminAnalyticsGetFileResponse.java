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
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

@Slf4j
@Data
public class AdminAnalyticsGetFileResponse implements SlackApiBinaryResponse {

    private boolean ok;
    private String error;
    private ResponseMetadata responseMetadata;
    private transient Map<String, List<String>> httpResponseHeaders;

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
        synchronized (loadedBytes) {
            if (loadedBytes.length == 0) {
                try (InputStream is = getFileStream()) {
                    if (is == null) {
                        // tried to read the input stream but it seems to be already closed
                        throw new IOException("The byte stream has been already consumed.");
                    }
                    try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                        byte[] data = new byte[1024];
                        int readSize;
                        while ((readSize = is.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, readSize);
                        }
                        buffer.flush();
                        loadedBytes = buffer.toByteArray();
                    }
                } finally {
                    // already consumed
                    setFileStream(null);
                }
            }
            return loadedBytes;
        }
    }

    public void forEach(Consumer<AnalyticsData> handler) throws IOException {
        this.forEach(GsonFactory.createSnakeCase(), handler);
    }

    public void forEach(Gson gson, Consumer<AnalyticsData> handler) throws IOException {
        InputStream is = getFileStream();
        if (loadedBytes.length > 0) {
            // already the whole data is loaded in heap memory
            is = new ByteArrayInputStream(loadedBytes);
        }
        if (is == null) {
            // the input stream is already consumed by either forEach or asBytes
            throw new IOException("The byte stream has been already consumed.");
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
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    @Data
    public static class OriginatingTeam {
        private String teamId;
        private String name;
    }

    @Data
    public static class SharedWith {
        private String teamId;
        private String name;
    }

    @Data
    public static class Organization {
        private String name;
        private String domain;
    }

    /**
     * Parsed Analytics Data
     */
    @Data
    public static class AnalyticsData {

        /**
         * (member)
         * (public_channel)
         * The date this row of data is representative of
         * (public_channel + metadata_only)
         * These details are current as of this date, which is also when you're making this API call
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
         * User ID
         */
        private String userId;

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
         * (member)
         * User is classified as a guest (not a full workspace member) on the date in the API request
         * Example: false
         */
        private Boolean isGuest;

        /**
         * (member)
         * User is classified as a billable user (included in the bill) on the date in the API request
         * Example: false
         */
        private Boolean isBillableSeat;

        /**
         * (member)
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request
         * Example: true
         */
        private Boolean isActive;

        /**
         * (member)
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request via the Slack iOS App
         * Example: true
         */
        private Boolean isActiveIos;

        /**
         * (member)
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request via the Slack Android App
         * Example: false
         */
        private Boolean isActiveAndroid;

        /**
         * (member)
         * User has posted a message or read at least one channel
         * or direct message on the date in the API request via the Slack Desktop App
         * Example: true
         */
        private Boolean isActiveDesktop;

        /**
         * (member)
         * Total reactions added to any message type in any conversation type
         * by the user on the date in the API request. Removing reactions is not included.
         * This metric is not de-duplicated by message—if a user adds 3 different reactions
         * to a single message, we will report 3 reactions
         * (public_channel)
         * A count of emoji reactions left on any message in channel on that given day by human users
         * Example: 20
         */
        private Integer reactionsAddedCount;

        /**
         * (member)
         * Total messages posted by the user on the date in the API request to all message and conversation types,
         * whether public, private, multi-person direct message, etc.
         * (public_channel)
         * A count of total messages posted, including messages from apps and integration on the given day
         * Example: 40
         */
        private Integer messagesPostedCount;

        /**
         * (member)
         * Total messages posted by the user in private channels and public channels on the date in the API request,
         * not including direct messages
         * Example: 30
         */
        private Integer channelMessagesPostedCount;

        /**
         * (member)
         * Total files uploaded by the user on the date in the API request
         * Example: 5
         */
        private Integer filesAddedCount;

        /**
         * (member)
         * Returns true when this member has interacted with a Slack app
         * or custom integration on the given day,
         * or if such an app or integration has performed an action on the user's behalf,
         * such as updating their custom status
         */
        private Boolean isActiveApps;

        /**
         * (member)
         * Returns true when this member has interacted with at least one workflow on a given day
         */
        private Boolean isActiveWorkflows;

        /**
         * (member)
         * Returns true when the member is considered active on Slack Connect,
         * in that they've read or posted a message to a channel
         * or direct message shared with at least one external workspace
         */
        private Boolean isActiveSlackConnect;

        /**
         * (member)
         * Total number of calls made or joined on a given day, including Huddles,
         * Slack native calls and calls made using third party software besides Slack calls
         */
        private Integer totalCallsCount;

        /**
         * (member)
         * Total number of Slack calls made or joined on a given day,
         * excluding any calls using third party software besides Slack calls
         */
        private Integer slackCallsCount;

        /**
         * (member)
         * Total number of Slack Huddles made or joined on a given day
         */
        private Integer slackHuddlesCount;

        /**
         * (member)
         * Total number of Slack Huddles made or joined on a given day
         * NOTE: "slack_huddles_count" field used to be "huddles_count"
         */
        @Deprecated
        private Integer huddlesCount;

        /**
         * (member)
         * The number of searches this user performed in Slack on a given day
         */
        private Integer searchCount;

        /**
         * (member)
         * The date of the very first time the member signed in
         * to any workspace within the grid organization,
         * presented in seconds since the epoch (UNIX time)
         */
        private Integer dateClaimed;

        // public_channel type

        /**
         * (public_channel)
         * A JSON object with the team_id and name of
         * the workspace that created this public channel
         * Example: {"team_id":"T4C3G041C","name":"arcane"}
         */
        private OriginatingTeam originatingTeam;

        /**
         * (public_channel)
         * The unique id belonging to this channel.
         * The metadata_only mode will give you information like the channel's name
         * Example: CNGL0K091
         * (public_channel + metadata_only)
         * The channel's unique identifier
         */
        private String channelId;

        /**
         * (public_channel)
         * Indicates which kind of public channel this is:
         * single_workspace_channel, multi_workspace_channel, or org_wide_channel.
         * Example: "single_workspace_channel"
         */
        private String channelType;

        /**
         * (public_channel)
         * Indicates whether the channel is public or private.
         * Only public channel analytics is available at this time.
         * Example: "public"
         */
        private String visibility;

        /**
         * (public_channel)
         * Indicates which, if any, workspaces in the same organization this channel is shared with.
         * Presented as an array of JSON objects containing a team_id and a name.
         * Only works with channel_type set to multi_channel_workspace.
         * One of the included team_id values corresponds to the organization itself, such as this E123457 example.
         * Example: [{"team_id":"T123456","name":"pentameter"},{"team_id":"E123457","name":"markov corp"}]
         */
        private List<SharedWith> sharedWith;

        /**
         * (public_channel)
         * A boolean value revealing whether the channel is shared with
         * workspaces outside of this organization when set to true.
         * Shared channel analytics are not yet available.
         * Example: false
         */
        private Boolean isSharedExternally;

        /**
         * (public_channel)
         * The date and time the channel was first created,
         * presented in seconds since the epoch (UNIX time).
         * Example: 1452719593
         */
        private Integer dateCreated;

        /**
         * (public_channel)
         * The date and time the channel last had a message posted in it,
         * presented in seconds since the epoch (UNIX time).
         * Example: 1584820530
         */
        private Integer dateLastActive;

        /**
         * (public_channel)
         * A count of total full members & guests
         * Example: 7
         */
        private Integer totalMembersCount;

        /**
         * (public_channel)
         * A count of people in this channel who have a Full Member account.
         * Example: 6
         */
        private Integer fullMembersCount;

        /**
         * (public_channel)
         * A count of people in this channel who have read or posted a message.
         * Example: 6
         */
        private Integer activeMembersCount;

        /**
         * (public_channel)
         * A count of people in this channel who have a guest account.
         * Example: 1
         */
        private Integer guestMembersCount;

        /**
         * (public_channel)
         * A count of total messages posted from Slack Members & guests (human users only)
         * on the given day
         * Example: 193
         */
        private Integer messagesPostedByMembersCount;

        /**
         * (public_channel)
         * A count of the unique number of human users (guests & full members)
         * who posted a message on the given day
         * Example: 3
         */
        private Integer membersWhoPostedCount;

        /**
         * (public_channel)
         * A count of the unique human users (guests & full members)
         * who read a message on the given day
         * Example: 225
         */
        private Integer membersWhoViewedCount;

        // public_channel type + metadata_only

        /**
         * (public_channel + metadata_only)
         * The latest name of the channel
         * Example: "ama"
         */
        private String name;

        /**
         * (public_channel + metadata_only)
         * The latest channel topic
         * Example: "Are Jack Kerouac's paragraphs too long?"
         */
        private String topic;

        /**
         * (public_channel + metadata_only)
         * A longer description about the purpose of the channel
         * Example: "Ask our editors all about their favorite literature"
         */
        private String description;

        /**
         * (public_channel)
         * Indicates which, if any, external organizations this channel is shared with.
         * Presented as an array of JSON objects containing an organization name and a slack domain.
         * Only works with is_shared_externally set to true.
         * Example: [{"name":"Away Org","domain":"away.enterprise.slack.com"}]
         */
        private List<Organization> externallySharedWithOrganizations;

    }

}
