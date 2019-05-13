package com.github.seratch.jslack;

import com.github.seratch.jslack.shortcut.Shortcut;
import com.github.seratch.jslack.shortcut.impl.ShortcutImpl;
import com.github.seratch.jslack.shortcut.model.ApiToken;

public class SlackShortcut {

    private final Slack slack;

    public SlackShortcut(Slack slack) {
        this.slack = slack;
    }

    public Shortcut getInstance() {
        return new ShortcutImpl(this.slack);
    }

    public Shortcut getInstance(ApiToken apiToken) {
        return new ShortcutImpl(this.slack, apiToken);
    }

}
