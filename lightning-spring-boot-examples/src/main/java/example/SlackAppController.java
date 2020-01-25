package example;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.servlet.SlackAppServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackAppController extends SlackAppServlet {
    public SlackAppController(App app) {
        super(app);
    }
}
