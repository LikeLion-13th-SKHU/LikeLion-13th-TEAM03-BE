package team3.sambakja.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import team3.sambakja.common.apiPayload.exception.GeneralException;
import team3.sambakja.common.apiPayload.status.ErrorStatus;
import team3.sambakja.dto.request.InquiryRequest;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final JavaMailSender mailSender;

    @Value("${inquiry.notify-email}")
    private String notifyEmail;

    public String sendInquiryEmail(InquiryRequest request) {
        String body = buildContent(request);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notifyEmail);
            message.setSubject("[삼박자] 문의 접수 : " + request.name());
            message.setText(body);

            mailSender.send(message);
            return body;

        } catch (MailException e) {
            throw new GeneralException(ErrorStatus.INQUIRY_MAIL_SEND_FAILED);
        }
    }

    private String buildContent(InquiryRequest request) {
        return String.format("""
            [삼박자 문의 내용]

            이름: %s
            연락처: %s
            이메일: %s
            문의 내용: %s
        """, request.name(), request.phone(), request.email(), request.message());
    }
}
