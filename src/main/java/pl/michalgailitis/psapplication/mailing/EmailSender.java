package pl.michalgailitis.psapplication.mailing;

public interface EmailSender {

    void sendMail(String to, String subject, String content);
}
