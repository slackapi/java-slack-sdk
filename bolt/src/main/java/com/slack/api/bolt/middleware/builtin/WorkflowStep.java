package com.slack.api.bolt.middleware.builtin;

import com.slack.api.bolt.handler.builtin.WorkflowStepEditHandler;
import com.slack.api.bolt.handler.builtin.WorkflowStepExecuteHandler;
import com.slack.api.bolt.handler.builtin.WorkflowStepSaveHandler;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.builtin.WorkflowStepEditRequest;
import com.slack.api.bolt.request.builtin.WorkflowStepExecuteRequest;
import com.slack.api.bolt.request.builtin.WorkflowStepSaveRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStep implements Middleware, AutoCloseable {

    private String callbackId;
    private Pattern callbackIdPattern;
    private WorkflowStepEditHandler edit;
    private WorkflowStepSaveHandler save;
    private WorkflowStepExecuteHandler execute;

    @Builder.Default
    private boolean executeAutoAcknowledgement = true;

    // If a developer would like to use their own ExecutorService here,
    // the recommended way would be to pass the one using the builder method:
    // `WorkflowStep.builder().executorService(executorService).build()`
    @Builder.Default
    private ExecutorService executorService = buildDefaultExecutorService();

    @Override
    public void close() {
        this.executorService.shutdown();
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("The WorkflowStep middleware started (request type: {})", req.getRequestType());
        }
        try {
            switch (req.getRequestType()) {
                case WorkflowStepEdit: {
                    WorkflowStepEditRequest request = (WorkflowStepEditRequest) req;
                    String requestCallbackId = request.getPayload().getCallbackId();
                    if (requestCallbackId != null) {
                        if (isStepCallbackId(requestCallbackId)) {
                            return edit.apply(request, request.getContext());
                        }
                        if (log.isDebugEnabled()) {
                            log.debug("callback_id: {} didn't match in the WorkflowStep middleware", requestCallbackId);
                        }
                    }
                    break;
                }
                case WorkflowStepSave: {
                    WorkflowStepSaveRequest request = (WorkflowStepSaveRequest) req;
                    String requestCallbackId = request.getPayload().getView().getCallbackId();
                    if (requestCallbackId != null) {
                        if (isStepCallbackId(requestCallbackId)) {
                            return save.apply(request, request.getContext());
                        }
                        if (log.isDebugEnabled()) {
                            log.debug("callback_id: {} didn't match in the WorkflowStep middleware", requestCallbackId);
                        }
                    }
                    break;
                }
                case WorkflowStepExecute: {
                    WorkflowStepExecuteRequest request = (WorkflowStepExecuteRequest) req;
                    String requestCallbackId = request.getContext().getCallbackId();
                    if (requestCallbackId != null) {
                        if (isStepCallbackId(requestCallbackId)) {
                            if (isExecuteAutoAcknowledgement()) {
                                // As the workflows.stepCompleted is really slow (it always takes 3+ seconds),
                                // This middleware automatically runs the API call asynchronously
                                // If you want to use your own mechanism to handle this, use your own middleware
                                // or add your primitive listener by App#worflowStepExecute() method
                                this.executorService.execute(() -> {
                                    try {
                                        execute.apply(request, request.getContext());
                                    } catch (Exception e) {
                                        log.error("Failed to run listener for a workflow_step_execute event ({}): {}",
                                                requestCallbackId, e.getMessage(), e);
                                    }
                                });
                                // Auto acknowledgement
                                return new Response();
                            } else {
                                // In the `execute` handler, workflows.stepCompleted/stepFailed
                                // needs to be called asynchronously
                                return execute.apply(request, request.getContext());
                            }
                        }
                        if (log.isDebugEnabled()) {
                            log.debug("callback_id: {} didn't match in the WorkflowStep middleware", requestCallbackId);
                        }
                    }
                    break;
                }
                default:
            }
            // continue with other global middleware and the default listener execution
            return chain.next(req);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("The WorkflowStep middleware completed (request type: {})", req.getRequestType());
            }
        }
    }

    protected boolean isStepCallbackId(String requestCallbackId) {
        return (getCallbackIdPattern() != null
                && getCallbackIdPattern().matcher(requestCallbackId).matches())
                || getCallbackId().equals(requestCallbackId);
    }

    protected static ExecutorService buildDefaultExecutorService() {
        String threadGroupName = WorkflowStep.class.getSimpleName();
        int poolSize = 3;
        // If you want to use own ExecutorService, pass it using the builder method instead:
        // `WorkflowStep.builder().executorService(executorService).build()`
        ExecutorService service = DaemonThreadExecutorServiceProvider.getInstance().createThreadPoolExecutor(
                threadGroupName,
                poolSize
        );
        return service;
    }
}
