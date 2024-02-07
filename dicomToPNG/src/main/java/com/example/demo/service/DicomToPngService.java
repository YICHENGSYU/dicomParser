package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface DicomToPngService {

//    利用提供的路徑讀取本地的dicom檔後取出所有圖片，回傳第{imgNumber}張圖片的base64字串
    String dicomToPng(String dcmPath, Integer imgNumber);

//    上傳dicom檔後取出所有圖片，回傳第{imgNumber}張圖片的base64字串
    String dicomToPngUpload(MultipartFile multipartFile,Integer imgNumber);

}
