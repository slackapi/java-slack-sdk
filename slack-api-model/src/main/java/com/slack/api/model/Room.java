package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private String id;
    private String name;
    private String mediaServer;
    private String createdBy;
    private Integer dateStart;
    private Integer dateEnd;
    private List<String> participants;
    private List<String> participantHistory;
    private List<String> participantsCameraOn;
    private List<String> participantsCameraOff;
    private List<String> participantsScreenshareOn;
    private List<String> participantsScreenshareOff;
    private String canvasThreadTs;
    private String threadRootTs;
    private List<String> channels;
    private Boolean isDmCall;
    private Boolean wasRejected;
    private Boolean wasMissed;
    private Boolean wasAccepted;
    private Boolean hasEnded;
    private String backgroundId;
    private String canvasBackground;
    private Boolean isPrewarmed;
    private Boolean isScheduled;
    private List<String> attachedFileIds;
    private String mediaBackendType;
    private String displayId;
    private String externalUniqueId;
    private String appId;
    private String callFamily;
    private Map<String, Object> pendingInvitees;
    private Map<String, Object> lastInviteStatusByUser;
    private Map<String, Object> knocks;
    private Map<String, ParticipantsEvent> participantsEvents;

    @Data
    public static class ParticipantsEvent {
        private boolean joined;
        private boolean cameraOn;
        private boolean cameraOff;
        private boolean screenshareOn;
        private boolean screenshareOff;
    }
}
