package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.OverflowMenuElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Displays authentication provider information, including its icon, name, and connection state.
 *
 * https://docs.slack.dev/reference/block-kit/blocks/auth-provider-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthProviderBlock implements LayoutBlock {
    public static final String TYPE = "auth_provider";
    private final String type = TYPE;

    /**
     * Unique identifier for this provider within the app. Included in interaction payloads and used to derive
     * the connect button's {@code action_id} ({@code auth_provider_connect_<value>}).
     * Maximum length for this field is 150 characters.
     */
    private String value;

    /**
     * Human-readable name of the provider.
     * Maximum length for the text in this field is 75 characters.
     */
    private PlainTextObject name;

    /**
     * Whether this provider is currently connected. Slack records this state for the user.
     */
    private Boolean isConnected;

    /**
     * Brief description of what this connection enables.
     * Maximum length for the text in this field is 150 characters.
     */
    private PlainTextObject description;

    /**
     * URL for the provider icon image. Must be https. Falls back to the app's avatar when absent.
     * Maximum length for this field is 3000 characters.
     */
    private String iconUrl;

    /**
     * OAuth redirect URL opened when the user clicks Connect. Must be https, on a host listed in the
     * app manifest's {@code features.user_connections.auth_hosts}.
     * Maximum length for this field is 3000 characters.
     */
    private String authUrl;

    /**
     * URL opened when the user clicks Disconnect. Must be https, on a host listed in the
     * app manifest's {@code features.user_connections.auth_hosts}.
     * Maximum length for this field is 3000 characters.
     */
    private String disconnectUrl;

    /**
     * Display name of the connected account, shown when {@code is_connected} is true.
     * Maximum length for the text in this field is 75 characters.
     */
    private PlainTextObject connectedIdentity;

    /**
     * Additional context about the connection (e.g., org or workspace name).
     * Maximum length for the text in this field is 75 characters.
     */
    private PlainTextObject connectedContext;

    /**
     * Overflow menu shown when connected (e.g., Disconnect, Re-authenticate).
     */
    private OverflowMenuElement accessory;

    /**
     * Stable blocks rendered by clients that don't recognize {@code auth_provider}, spliced into the parent
     * blocks array.
     */
    private List<LayoutBlock> fallback;

    private String blockId;
}
