package com.github.seratch.jslack;

import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sample_json_generation.ObjectToJsonDumper;
import util.ObjectInitializer;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class SlashCommandPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("./json-logs/samples/app-backend/slash-commands");

    @Test
    public void dumpAll() throws Exception {
        List<Object> payloads = Arrays.asList(
                new SlashCommandPayload()
        );
        for (Object payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

}
