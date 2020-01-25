package test_locally.util;

import com.github.seratch.jslack.lightning.util.JsonOps;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonOpsTest {

    @Data
    @AllArgsConstructor
    public static class Name {
        private String first;
        private String last;
    }

    @Test
    public void toJsonString_string() {
        String jsonStr = "{\"error\":\"Something wrong\"}";
        String result = JsonOps.toJsonString(jsonStr);
        assertThat(result, is(jsonStr));
    }

    @Test
    public void toJsonString_object() {
        Name name = new Name("Kazuhiro", "Sera");
        String result = JsonOps.toJsonString(name);
        assertThat(result, is("{\"first\":\"Kazuhiro\",\"last\":\"Sera\"}"));
    }

    @Test
    public void toJson_string() {
        String jsonStr = "{\"error\":\"Something wrong\"}";
        JsonElement result = JsonOps.toJson(jsonStr);
        assertThat(result.getAsJsonObject().get("error").getAsString(), is("Something wrong"));
        assertThat(result.getAsJsonObject().get("ok"), is(nullValue()));
    }

    @Test
    public void toJson_object() {
        Name name = new Name("Kazuhiro", "Sera");
        JsonElement result = JsonOps.toJson(name);
        assertThat(result.getAsJsonObject().get("first").getAsString(), is("Kazuhiro"));
        assertThat(result.getAsJsonObject().get("lastName"), is(nullValue()));
    }

}
