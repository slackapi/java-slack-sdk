package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.functions.FunctionsCompleteErrorResponse;
import com.slack.api.methods.response.functions.FunctionsCompleteSuccessResponse;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FunctionUtility {

    String getFunctionExecutionId();

    MethodsClient client();

    default FunctionsCompleteSuccessResponse complete(Map<String, ?> outputs) throws IOException, SlackApiException {
        return this.client().functionsCompleteSuccess(r -> r
                .functionExecutionId(this.getFunctionExecutionId())
                .outputs(outputs)
        );
    }

    default FunctionsCompleteErrorResponse fail(String error) throws IOException, SlackApiException {
        return this.client().functionsCompleteError(r -> r
                .functionExecutionId(this.getFunctionExecutionId())
                .error(error)
        );
    }

}
