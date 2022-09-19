package example.idomatic;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.micronaut.handlers.MicronautSlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import jakarta.inject.Singleton;

@Singleton
public class HelloCommand implements MicronautSlashCommandHandler {

    @Override
    public String getCommandId() {
        return "/hello";
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext context) {
        return context.ack(r -> r.text("Thanks!"));
    }

}
