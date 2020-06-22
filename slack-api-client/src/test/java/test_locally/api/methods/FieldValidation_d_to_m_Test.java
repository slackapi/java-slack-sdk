package test_locally.api.methods;

import com.slack.api.methods.response.dialog.DialogOpenResponse;
import com.slack.api.methods.response.dnd.*;
import com.slack.api.methods.response.emoji.EmojiListResponse;
import com.slack.api.methods.response.files.*;
import com.slack.api.methods.response.files.remote.*;
import com.slack.api.methods.response.groups.*;
import com.slack.api.methods.response.im.*;
import com.slack.api.methods.response.mpim.*;
import com.slack.api.model.File;
import com.slack.api.model.Group;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.FileReader;

import java.io.IOException;

import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNull;
import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNullRecursively;

@Slf4j
public class FieldValidation_d_to_m_Test {

    private FileReader reader = new FileReader("../json-logs/samples/api/");

    private <T> T parse(String path, Class<T> clazz) throws IOException {
        return GsonFactory.createSnakeCase().fromJson(reader.readWholeAsString(path + ".json"), clazz);
    }

    @Test
    public void dialog_open() throws Exception {
        DialogOpenResponse obj = parse("dialog.open", DialogOpenResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
    }

    @Test
    public void dnd_endDnd() throws Exception {
        DndEndDndResponse obj = parse("dnd.endDnd", DndEndDndResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void dnd_endSnooze() throws Exception {
        DndEndSnoozeResponse obj = parse("dnd.endSnooze", DndEndSnoozeResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void dnd_info() throws Exception {
        DndInfoResponse obj = parse("dnd.info", DndInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getSnoozeEndtime",
                "getSnoozeRemaining"
        );
    }

    @Test
    public void dnd_setSnooze() throws Exception {
        DndSetSnoozeResponse obj = parse("dnd.setSnooze", DndSetSnoozeResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void dnd_teamInfo() throws Exception {
        DndTeamInfoResponse obj = parse("dnd.teamInfo", DndTeamInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNull(obj.getUsers().values().iterator().next());
    }

    @Test
    public void emoji_list() throws Exception {
        {
            EmojiListResponse obj = parse("emoji.list", EmojiListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    void verifyFile(File file) throws Exception {
        verifyIfAllGettersReturnNonNull(file,
                "getBotId",
                "getSubject",
                "getExternalId",
                "getExternalUrl",
                "getAppId",
                "getAppName",
                "getThumb.+$",
                "getConvertedPdf",
                "getDeanimateGif",
                "getPjpeg",
                "getPlainText",
                "getPreviewPlainText",
                "getInitialComment",
                "getNumStars",
                "getPinnedTo",
                "getPinnedInfo",
                "getReactions",
                "getChannelActionsTs",
                "getChannelActionsCount",
                "getAttachments",
                "getBlocks",
                "getTo",
                "getFrom",
                "getCc",
                "getShares",
                "getEditor",
                "getLastEditor",
                "getUpdated"
        );
    }

    @Test
    public void files() throws Exception {
        {
            FilesDeleteResponse obj = parse("files.delete", FilesDeleteResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            FilesInfoResponse obj = parse("files.info", FilesInfoResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyFile(obj.getFile());
        }
        {
            FilesListResponse obj = parse("files.list", FilesListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyFile(obj.getFiles().get(0));
        }
        {
            FilesRevokePublicURLResponse obj = parse("files.revokePublicURL", FilesRevokePublicURLResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyFile(obj.getFile());
        }
        {
            FilesSharedPublicURLResponse obj = parse("files.sharedPublicURL", FilesSharedPublicURLResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning",
                    "getComments",
                    "getPaging"
            );
            verifyFile(obj.getFile());
        }
        {
            FilesUploadResponse obj = parse("files.upload", FilesUploadResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyFile(obj.getFile());
        }
    }

    void verifyRemoteFile(File file) throws Exception {
        verifyIfAllGettersReturnNonNull(file,
                "getBotId",
                "getSubject",
                "getExternalId",
                "getExternalUrl",
                "getAppId",
                "getAppName",
                "getThumb.+$",
                "getConvertedPdf",
                "getDeanimateGif",
                "getPjpeg",
                "getPlainText",
                "getPreviewPlainText",
                "getInitialComment",
                "getNumStars",
                "getPinnedTo",
                "getPinnedInfo",
                "getReactions",
                "getChannelActionsTs",
                "getChannelActionsCount",
                "getAttachments",
                "getBlocks",
                "getTo",
                "getFrom",
                "getCc",
                "getShares",
                // files.remote
                "getUrlPrivateDownload",
                "getPermalinkPublic",
                "getImageExifRotation",
                "getOriginalWidth",
                "getOriginalHeight",
                "getEditLink",
                "getPreview",
                "getPreviewHighlight",
                "getLines",
                "getLinesMore",
                "getEditor",
                "getLastEditor",
                "getUpdated"
        );
    }

    @Test
    public void files_remote() throws Exception {
        {
            FilesRemoteAddResponse obj = parse("files.remote.add", FilesRemoteAddResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyRemoteFile(obj.getFile());
        }
        {
            FilesRemoteInfoResponse obj = parse("files.remote.info", FilesRemoteInfoResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyRemoteFile(obj.getFile());
        }
        {
            FilesRemoteListResponse obj = parse("files.remote.list", FilesRemoteListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyRemoteFile(obj.getFiles().get(0));
        }
        {
            FilesRemoteRemoveResponse obj = parse("files.remote.remove", FilesRemoteRemoveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            FilesRemoteShareResponse obj = parse("files.remote.share", FilesRemoteShareResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyRemoteFile(obj.getFile());
        }
        {
            FilesRemoteUpdateResponse obj = parse("files.remote.update", FilesRemoteUpdateResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyRemoteFile(obj.getFile());
        }
    }

    private void validateGroup(Group group) throws Exception {
        verifyIfAllGettersReturnNonNullRecursively(group,
                "getEnterpriseId",
                "getParentGroup",
                "getUser",
                "getPriority",
                "getRoot",
                "getIsMoved",
                "getPendingShared",
                "getNumMembers",
                "getUnreadCount",
                "getUnreadCountDisplay",
                "getLatest",
                // latest
                "getTeam",
                "getAttachments",
                "getBlocks",
                "getTopic",
                "getClientMsgId",
                "getThreadTs",
                "getReactions",
                "getIcons",
                "getFiles",
                "getBotId",
                "getBotLink",
                "getXFiles",
                "getUsername",
                "getLastRead",
                "getMembers"
        );
    }

    @Test
    public void groups() throws Exception {
        String prefix = "groups.";
        {
            GroupsArchiveResponse obj = parse(prefix + "archive", GroupsArchiveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsCloseResponse obj = parse(prefix + "close", GroupsCloseResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsCreateResponse obj = parse(prefix + "create", GroupsCreateResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getGroup(),
                    "getParentGroup",
                    "getEnterpriseId",
                    "getLatest",
                    "getUser",
                    "getIsMoved",
                    "getPendingShared",
                    "getNumMembers");
        }
        {
            GroupsCreateChildResponse obj = parse(prefix + "createChild", GroupsCreateChildResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getGroup(),
                    "getEnterpriseId",
                    "getLatest",
                    "getUser",
                    "getIsMoved",
                    "getPendingShared",
                    "getPriority",
                    "getNumMembers");
        }
        {
            GroupsHistoryResponse obj = parse(prefix + "history", GroupsHistoryResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning",
                    "getChannelActionsTs",
                    "getLatest"
            );
        }
        {
            GroupsInfoResponse obj = parse(prefix + "info", GroupsInfoResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateGroup(obj.getGroup());
        }
        {
            GroupsInviteResponse obj = parse(prefix + "invite", GroupsInviteResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateGroup(obj.getGroup());
        }
        {
            GroupsKickResponse obj = parse(prefix + "kick", GroupsKickResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsLeaveResponse obj = parse(prefix + "leave", GroupsLeaveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsListResponse obj = parse(prefix + "list", GroupsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateGroup(obj.getGroups().get(0));
        }
        {
            GroupsMarkResponse obj = parse(prefix + "mark", GroupsMarkResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsOpenResponse obj = parse(prefix + "open", GroupsOpenResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsRenameResponse obj = parse(prefix + "rename", GroupsRenameResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            // TODO: verify obj.getChannel()
        }
        {
            GroupsRepliesResponse obj = parse(prefix + "replies", GroupsRepliesResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getThreadInfo");
        }
        {
            GroupsSetPurposeResponse obj = parse(prefix + "setPurpose", GroupsSetPurposeResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsSetTopicResponse obj = parse(prefix + "setTopic", GroupsSetTopicResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            GroupsUnarchiveResponse obj = parse(prefix + "unarchive", GroupsUnarchiveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    @Test
    public void im_close() throws Exception {
        String prefix = "im.";
        ImCloseResponse obj = parse(prefix + "close", ImCloseResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void im_history() throws Exception {
        String prefix = "im.";
        ImHistoryResponse obj = parse(prefix + "history", ImHistoryResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getChannelActionsTs",
                "getLatest"
        );
    }

    @Test
    public void im_list() throws Exception {
        String prefix = "im.";
        ImListResponse obj = parse(prefix + "list", ImListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void im_mark() throws Exception {
        String prefix = "im.";
        ImMarkResponse obj = parse(prefix + "mark", ImMarkResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void im_open() throws Exception {
        String prefix = "im.";
        ImOpenResponse obj = parse(prefix + "open", ImOpenResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void im_replies() throws Exception {
        String prefix = "im.";
        ImRepliesResponse obj = parse(prefix + "replies", ImRepliesResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void mpim_close() throws Exception {
        String prefix = "mpim.";
        MpimCloseResponse obj = parse(prefix + "close", MpimCloseResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void mpim_history() throws Exception {
        String prefix = "mpim.";
        MpimHistoryResponse obj = parse(prefix + "history", MpimHistoryResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getChannelActionsTs",
                "getLatest"
        );
    }

    @Test
    public void mpim_list() throws Exception {
        String prefix = "mpim.";
        MpimListResponse obj = parse(prefix + "list", MpimListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void mpim_mark() throws Exception {
        String prefix = "mpim.";
        MpimMarkResponse obj = parse(prefix + "mark", MpimMarkResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void mpim_open() throws Exception {
        String prefix = "mpim.";
        MpimOpenResponse obj = parse(prefix + "open", MpimOpenResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void mpim_replies() throws Exception {
        String prefix = "mpim.";
        MpimRepliesResponse obj = parse(prefix + "replies", MpimRepliesResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }
}
