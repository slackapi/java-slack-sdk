package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The file_unshared event is sent when a file is unshared.
 * It is sent to all connected clients for all users that had permission to see the file.
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the unshared file, use the files.info API method.
 * <p>
 * https://api.slack.com/events/goodbye
 */
@Data
public class GoodbyeEvent implements Event {

    public static final String TYPE_NAME = "goodbye";

    private final String type = TYPE_NAME;

}