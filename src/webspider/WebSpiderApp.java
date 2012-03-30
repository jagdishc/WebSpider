package webspider;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

public class WebSpiderApp extends SingleFrameApplication {

    @Override protected void startup() {
        show(new WebSpiderView(this));
    }

    @Override protected void configureWindow(java.awt.Window root) {
    }

    public static WebSpiderApp getApplication() {
        return Application.getInstance(WebSpiderApp.class);
    }

    public static void main(String[] args) {        
        launch(WebSpiderApp.class, args);
    }
}
