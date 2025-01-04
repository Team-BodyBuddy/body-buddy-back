package BodyBuddy.demo.domain.memberInbody.controller;

import BodyBuddy.demo.domain.memberInbody.dto.InbodyResponseDto;
import BodyBuddy.demo.domain.memberInbody.service.InbodyService;
import BodyBuddy.demo.global.apiPayLoad.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/inbody")
public class InbodyController {

    private final InbodyService inbodyService;

    public InbodyController(InbodyService inbodyService) {
        this.inbodyService = inbodyService;
    }

    @PostMapping("/{memberId}/upload")
    public ResponseEntity<ApiResponse<InbodyResponseDto>> uploadInbodyPdf(
        @PathVariable Long memberId,
        @RequestParam("file") MultipartFile pdfFile) {
        InbodyResponseDto responseDto = inbodyService.uploadPdf(memberId, pdfFile);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    @GetMapping("/{memberId}/last-date")
    public ResponseEntity<ApiResponse<String>> getLastUploadDate(@PathVariable Long memberId) {
        String lastDate = inbodyService.getLastUploadDate(memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess(lastDate));
    }
}

