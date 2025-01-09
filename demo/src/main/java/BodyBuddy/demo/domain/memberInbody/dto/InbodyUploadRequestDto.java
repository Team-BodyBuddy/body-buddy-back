package BodyBuddy.demo.domain.memberInbody.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class InbodyUploadRequestDto {
    private MultipartFile pdfFile;
}