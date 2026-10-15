package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.OverflowMenuElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Displays an authentication provider with its icon, name, and connection state, plus a Connect or
 * Disconnect button that Slack renders.
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
     * Unique identifier for this provider within the app, up to 150 characters. Included in interaction
     * payloads; the connect button's {@code action_id} is derived from it ({@code auth_provider_connect_<value>}).
     * Required.
     */
    private String value;

    /**
     * Human-readable name of the provider, up to 75 characters. Required.
     */
    private PlainTextObject name;

    /**
     * Whether this provider is currently connected. Required: Slack records this state for the user,
     * so an omitted value would read as disconnected.
     */
    private Boolean isConnected;

    /**
     * Brief description of what this connection enables, up to 150 characters.
     */
    private PlainTextObject description;

    /**
     * URL for the provider icon image, up to 3000 characters. Must be https. Defaults to the app's avatar.
     */
    private String iconUrl;

    /**
     * OAuth redirect URL opened when the user clicks Connect. Must be https, on a host listed in the
     * app manifest's {@code features.user_connections.auth_hosts}.
     */
    private String authUrl;

    /**
     * URL opened when the user clicks Disconnect, up to 3000 characters. Must be https, on a host listed
     * in the app manifest's {@code features.user_connections.auth_hosts}.
     */
    private String disconnectUrl;

    /**
     * Display name of the connected account, shown when {@code is_connected} is true.
     */
    private PlainTextObject connectedIdentity;

    /**
     * Additional context about the connection (e.g., org or workspace name).
     */
    private PlainTextObject connectedContext;

    /**
     * Overflow menu shown when connected (e.g., Re-authenticate, Disconnect).
     */
    private OverflowMenuElement accessory;

    /**
     * Blocks rendered by clients that don't recognize {@code auth_provider}, spliced into the parent blocks array.
     */
    private List<LayoutBlock> fallback;

    private String blockId;
}
