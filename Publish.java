package server;

import javax.xml.ws.Endpoint;

public class Publish {
	public static void main(String[] args) {
		String address="http://localhost:9999/";
		Object implementer=new MailLogic();
		Endpoint.publish(address, implementer);
		System.out.println("服务发布成功！");
	}
}