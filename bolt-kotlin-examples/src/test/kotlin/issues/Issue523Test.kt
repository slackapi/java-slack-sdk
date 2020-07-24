package issues

import com.slack.api.Slack
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test
import java.io.File

class Issue523Test {

    private val slack = Slack.getInstance()
    private val token = System.getenv("SLACK_SDK_TEST_USER_TOKEN")
    private val slackMethods = slack.methods(token)

    @Test
    fun text_no_filename() {
        val file = File("src/test/resources/sample.txt")
        val response = slackMethods.filesUpload {
            it.fileData(file.readBytes()).filetype("text")
        }
        assertEquals("no_file_data", response.error)
    }

    @Test
    fun text_filename() {
        val file = File("src/test/resources/sample.txt")
        val response = slackMethods.filesUpload {
            it.fileData(file.readBytes()).filetype("text").filename("test.txt")
        }
        assertNull(response.error)
    }

    @Test
    fun image_no_filename() {
        val file = File("src/test/resources/sample.jpg")
        val response = slackMethods.filesUpload {
            it.fileData(file.readBytes()).filetype("jpg")
        }
        assertEquals("no_file_data", response.error)
    }

    @Test
    fun image() {
        val file = File("src/test/resources/sample.jpg")
        val response = slackMethods.filesUpload {
            it.fileData(file.readBytes()).filetype("jpg").filename("test.jpg")
        }
        assertNull(response.error)
    }

}