package listener;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

public class AddListener implements NotificationListener {

	@Override
	public void handleNotification(Notification notification, Object handback) {
		echo("Receive notification");
		echo("Classname: " + notification.getClass().getName());
		echo("Source: " + notification.getSource());
		echo("Type: " + notification.getType());
		echo("Message: " + notification.getMessage());
		if (notification instanceof AttributeChangeNotification) {
			AttributeChangeNotification acn = (AttributeChangeNotification) notification;
			echo("AttributeName: " + acn.getAttributeName());
			echo("AttributeType: " + acn.getAttributeType());
			echo("NewValue: " + acn.getNewValue());
			echo("Old Value: " + acn.getOldValue());
		}
		 
	}
	
	private static void echo(String msg) {
        System.out.println(msg + "\n");
    }

}
