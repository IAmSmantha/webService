# webService
问题描述：在现实生活中的系统，存在着大量发送手机短信通知，发送电子邮件消息的模块，例如：当你登录阿里云账号时，可以通过手机验证实现账号登入功能。这类通知服务是可以在其他应用程序上重用的功能或模板之一。
结合课堂上讲授的 SOA 风格以及阿里云的邮件服务，实现一个发送电子邮件消息的 Web
Service 服务，开发语言不限（推荐使用 Java）。这个 Web Service 服务要求实现以下几个
基本服务：
 sendEmail(String _url,String _payload) //邮件地址为_url，内容为_payload
 sendEmailBatch(String[] _url,String _payload) //批量发送邮件
 validateEmailAddress(String _url) //验证是否为有效的邮件地址

本次实习我将sendEmailBatch融合进了sendEmail，即它既可以实现发送单个邮件也可以实现批量发送。
