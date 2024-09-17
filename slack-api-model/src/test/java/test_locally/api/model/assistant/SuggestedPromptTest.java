package test_locally.api.model.assistant;

import com.slack.api.model.assistant.SuggestedPrompt;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SuggestedPromptTest {
    @Test
    public void test() {
        SuggestedPrompt s1 = new SuggestedPrompt("What does SLACK stand for?");
        assertThat(s1, is(notNullValue()));
        assertThat(s1.getTitle(), is("What does SLACK stand for?"));
        assertThat(s1.getMessage(), is("What does SLACK stand for?"));
        SuggestedPrompt s2 = new SuggestedPrompt("title", "message");
        assertThat(s2, is(notNullValue()));
        assertThat(s2.getTitle(), is("title"));
        assertThat(s2.getMessage(), is("message"));
        SuggestedPrompt s3 = SuggestedPrompt.create("What does SLACK stand for?");
        assertThat(s3, is(notNullValue()));
        assertThat(s3.getTitle(), is("What does SLACK stand for?"));
        assertThat(s3.getMessage(), is("What does SLACK stand for?"));
    }
}

