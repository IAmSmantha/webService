package server;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
@WebService
public class MailLogic {
    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final String ALIDM_SMTP_PORT = "25";//或"80"

	public boolean validateEmailAddress(String _url){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(RULE_EMAIL);
        Matcher m = p.matcher(_url);
        return m.matches();  
    }
	@WebMethod
	public void sendEmail(String _url,String _title,String content) throws UnsupportedEncodingException {
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", ALIDM_SMTP_HOST);
        props.put("mail.smtp.port", ALIDM_SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");      
        props.put("mail.user", "samantha@yansimin.xyz");
       props.put("mail.password", "XYsm20161003917");
       Authenticator authenticator = new Authenticator() {
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {
               String userName = props.getProperty("mail.user");
               String password = props.getProperty("mail.password");
               return new PasswordAuthentication(userName, password);
           }
       };
       Session mailSession = Session.getInstance(props, authenticator);
       mailSession.setDebug(true);
       UUID uuid = UUID.randomUUID();
       final String messageIDValue = "<" + uuid.toString() + ">";
       MimeMessage message = new MimeMessage(mailSession){
           @Override
           protected void updateMessageID() throws MessagingException {
               setHeader("Message-ID", messageIDValue);
           }
       };
       try {
	       InternetAddress from = new InternetAddress("samantha@yansimin.xyz", "yansimin");
	       message.setFrom(from);
	       Address[] a = new Address[1];
	       a[0] = new InternetAddress("samantha@yansimin.xyz");
	       message.setReplyTo(a);
	       String [] arr = _url.split(" ");
	       InternetAddress[] adds = new InternetAddress[arr.length];
	       for(int i=0;i<arr.length;i++) {
	    	   if(validateEmailAddress(arr[i]))
	    		   adds[i] = new InternetAddress(arr[i]);
	    	   else
	    		   System.out.println("第"+i+1+"个邮箱号码格式不正确！");
	       }
	       message.setRecipients(Message.RecipientType.TO, adds);
	       message.setSubject(_title);
	       message.setContent(content, "text/html;charset=UTF-8");
	       Transport.send(message);
       }
       catch (MessagingException e) {
           String err = e.getMessage();
           System.out.println(err);
       }
	}
}