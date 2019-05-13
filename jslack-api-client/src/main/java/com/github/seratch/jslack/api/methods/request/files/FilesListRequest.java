package com.github.seratch.jslack.api.methods.request.files;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilesListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:read`
     */
    private String token;

    /**
     * Filter files created by a single user.
     */
    private String user;

    /**
     * Filter files appearing in a specific channel, indicated by its ID.
     */
    private String channel;

    /**
     * Filter files created after this timestamp (inclusive).
     */
    private String tsFrom;

    /**
     * Filter files created before this timestamp (inclusive).
     */
    private String tsTo;

    /**
     * Filter files by type:\n\n* `all` - All files
     * <p>
     * `spaces` - Posts\n* `snippets` - Snippets
     * `images` - Image files
     * `gdocs` - Google docs
     * `zips` - Zip files
     * `pdfs` - PDF files
     * <p>
     * You can pass multiple values in the types argument, like `types=spaces,snippets`.
     * The default value is `all`, which does not filter the list.
     */
    private List<String> types;

    private Integer count;

    private Integer page;

}