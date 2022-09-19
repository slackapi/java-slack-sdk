package example.idomatic;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.micronaut.handlers.MicronautSlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import jakarta.inject.Singleton;

import java.util.regex.Pattern;

@Singleton
public class SubmissionNumberCommand implements MicronautSlashCommandHandler {

    @Override
    public Pattern getCommandIdPattern() {
        return Pattern.compile("/submission-no.\\d+");
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext context) {
        return context.ack(r -> r.text(slashCommandRequest.getPayload().getCommand()));
    }

}
