package pages;

import org.openqa.selenium.By;
import utils.Config;
import utils.Locators;

public class NotificationsPage {

    private final TestContext context;

    private static final By NOTIFY_ME = Locators.byExactText("button","Notify me");

    public NotificationsPage(TestContext context) {
        this.context = context;
    }

    public NotificationsPage open(){
        context.driver().get(Config.url("notifications.html"));
        return this;
    }

    public NotificationsPage notifyMeClick(){
        context.click(NOTIFY_ME);
        return this;
    }

    public String interceptNotification() {
        String script = """
                const callback = arguments[arguments.length -1];
                const OldNotify = window.Notification;
                function newNotification(title,options){
                    callback(title);
                    return new OldNotify(title,options);
                }
                newNotification.requestPermission = OldNotify.requestPermission.bind(OldNotify);
                Object.defineProperty(newNotification, 'permission', {
                    get: function(){
                        return OldNotify.permission;
                    }
                });
                window.Notification = newNotification;
                document.getElementById('notify-me').click();
                """;
        context.log().debug("Executing the following script asynchronously: \n{}",script);
        return (String) context.js().executeAsyncScript(script);
    }
}
