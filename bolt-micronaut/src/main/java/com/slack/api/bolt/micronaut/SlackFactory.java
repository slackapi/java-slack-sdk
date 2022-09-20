package com.slack.api.bolt.micronaut;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.micronaut.handlers.MicronautAttachmentActionHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautBlockActionHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautBlockSuggestionHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautBoltEventHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautDialogCancellationHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautDialogSubmissionHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautDialogSuggestionHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautGlobalShortcutHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautMessageShortcutHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautSlashCommandHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautViewClosedHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautViewSubmissionHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautWorkflowStepEditHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautWorkflowStepExecuteHandler;
import com.slack.api.bolt.micronaut.handlers.MicronautWorkflowStepSaveHandler;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.model.event.Event;
import com.slack.api.util.http.SlackHttpClient;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Secondary;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

import java.util.List;

/**
 * Creates all the necessary beans in the Micronaut context.
 */
@Factory
public class SlackFactory {

    @Bean
    @Secondary
    @Singleton
    public SlackConfig slackConfig() {
        return SlackConfig.DEFAULT;
    }

    @Bean
    @Secondary
    @Singleton
    public SlackHttpClient slackHttpClient(SlackConfig config) {
        return SlackHttpClient.buildSlackHttpClient(config);
    }

    @Bean
    @Secondary
    @Singleton
    public Slack slack() {
        return Slack.getInstance();
    }

    @Bean
    @Secondary
    @Singleton
    public MethodsClient methodsClient(Slack slack, SlackConfiguration configuration) {
        if (StringUtils.isNotEmpty(configuration.getSingleTeamBotToken())) {
            return slack.methods(configuration.getSingleTeamBotToken());
        }
        return slack.methods();
    }

    @Bean
    @Secondary
    @Singleton
    public AsyncMethodsClient asyncMethodsClientx(Slack slack, SlackConfiguration configuration) {
        if (StringUtils.isNotEmpty(configuration.getSingleTeamBotToken())) {
            return slack.methodsAsync(configuration.getSingleTeamBotToken());
        }
        return slack.methodsAsync();
    }

    @Bean
    @Secondary
    @Singleton
    public App app(
        SlackConfiguration configuration,
        Slack slack,
        List<MicronautAttachmentActionHandler> attachmentActionHandlers,
        List<MicronautBlockActionHandler> blockActionHandlers,
        List<MicronautBlockSuggestionHandler> blockSuggestionHandlers,
        List<MicronautDialogCancellationHandler> dialogCancellationHandlers,
        List<MicronautDialogSubmissionHandler> dialogSubmissionHandlers,
        List<MicronautDialogSuggestionHandler> dialogSuggestionHandlers,
        List<MicronautGlobalShortcutHandler> globalShortcutHandlers,
        List<MicronautMessageShortcutHandler> messageShortcutHandlers,
        List<MicronautSlashCommandHandler> slashCommandHandlers,
        List<MicronautViewClosedHandler> viewClosedHandlers,
        List<MicronautViewSubmissionHandler> viewSubmissionHandlers,
        List<MicronautWorkflowStepEditHandler> workflowStepEditHandlers,
        List<MicronautWorkflowStepExecuteHandler> workflowStepExecuteHandlers,
        List<MicronautWorkflowStepSaveHandler> workflowStepSaveHandlers,
        List<MicronautBoltEventHandler<Event>> boltEventHandlers
    ) {
        App app = createApp(configuration, slack);

        attachmentActionHandlers.forEach(h -> app.attachmentAction(h.getCallbackIdPattern(), h));
        blockActionHandlers.forEach(h -> app.blockAction(h.getActionIdPattern(), h));
        blockSuggestionHandlers.forEach(h -> app.blockSuggestion(h.getActionIdPattern(), h));
        dialogCancellationHandlers.forEach(h -> app.dialogCancellation(h.getCallbackIdPattern(), h));
        dialogSubmissionHandlers.forEach(h -> app.dialogSubmission(h.getCallbackIdPattern(), h));
        dialogSuggestionHandlers.forEach(h -> app.dialogSuggestion(h.getCallbackIdPattern(), h));
        globalShortcutHandlers.forEach(h -> app.globalShortcut(h.getCallbackIdPattern(), h));
        messageShortcutHandlers.forEach(h -> app.messageShortcut(h.getCallbackIdPattern(), h));
        slashCommandHandlers.forEach(h -> app.command(h.getCommandIdPattern(), h));
        viewClosedHandlers.forEach(h -> app.viewClosed(h.getCallbackIdPattern(), h));
        viewSubmissionHandlers.forEach(h -> app.viewSubmission(h.getCallbackIdPattern(), h));
        workflowStepEditHandlers.forEach(h -> app.workflowStepEdit(h.getCallbackIdPattern(), h));
        workflowStepExecuteHandlers.forEach(h -> app.workflowStepExecute(h.getPattern(), h));
        workflowStepSaveHandlers.forEach(h -> app.workflowStepSave(h.getCallbackIdPattern(), h));
        boltEventHandlers.forEach(h -> app.event(h.getEventType(), h));

        return app;
    }

    private static App createApp(SlackConfiguration configuration, Slack slack) {
        if (configuration.isOAuthInstallPathEnabled()) {
            return new App(configuration, slack, null).asOAuthApp(true);
        }

        if (configuration.isOpenIDConnectEnabled()) {
            return new App(configuration, slack, null).asOpenIDConnectApp(true);
        }

        return new App(configuration, slack, null);
    }

}
