package com.myintellij.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        // UUID(Universally Unique Identifier): 서로 다른 개체들을 구별하기 위해 이름 부여 시 사용
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // FileOutputStream: 바이트 단위의 출력을 내보내는 클래스.
        // 생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만듦
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName; // 업로드된 파일 이름을 반환
    }
    
    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath); // 파일이 저장된 경로를 이용해 파일 객체를 생성
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일 삭제");
        } else {
            log.info("파일이 미존재");
        }
    }
    
}
