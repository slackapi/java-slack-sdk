package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.OverflowMenuElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/auth-provider-block
 * <p>
 * Displays an authentication provider with its icon, name, and connection state.
 * Available in modals and Home tabs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthProviderBlock implements LayoutBlock {
    public static final String TYPE = "auth_provider";
    private final String type = TYPE;

    /**
     * Unique identifier for this provider within the app, up to 150 characters.
     * The connect button's `action_id` is derived from it (`auth_provider_connect_{value}`). Required.
     */
    private String value;

    /**
     * Human-readable name of the provider, up to 75 characters. Required.
     */
    private PlainTextObject name;

    /**
     * Whether this provider is currently connected. Required: Slack records this state against the user,
     * so an omitted value would be indistinguishable from an explicit false.
     */
    private Boolean isConnected;

    /**
     * Brief description of what this connection enables, up to 150 characters.
     */
    private PlainTextObject description;

    /**
     * URL for the provider icon image. Must be https. Defaults to the app's avatar.
     */
    private String iconUrl;

    /**
     * OAuth redirect URL opened when the user clicks Connect.
     * Must be https, on a host registered in the app manifest's `features.user_connections.auth_hosts`.
     */
    private String authUrl;

    /**
     * URL opened when the user clicks Disconnect.
     * Must be https, on a host registered in the app manifest's `features.user_connections.auth_hosts`.
     */
    private String disconnectUrl;

    /**
     * Display name of the connected account, up to 75 characters. Shown when `is_connected` is true.
     */
    private PlainTextObject connectedIdentity;

    /**
     * Additional context about the connection (e.g., org or workspace name), up to 75 characters.
     */
    private PlainTextObject connectedContext;

    /**
     * Overflow menu shown when connected (e.g., Switch accounts, Disconnect).
     */
    private OverflowMenuElement accessory;

    /**
     * Blocks rendered by clients that don't recognize `auth_provider`, in its place.
     */
    private List<LayoutBlock> fallback;

    /**
     * A string acting as a unique identifier for a block. If not specified, one will be generated.
     * Maximum length for this field is 255 characters.
     */
    private String blockId;
}
