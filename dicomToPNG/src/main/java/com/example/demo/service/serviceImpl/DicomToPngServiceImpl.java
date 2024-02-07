package com.example.demo.service.serviceImpl;

import com.example.demo.service.DicomToPngService;
import com.example.demo.service.util.DicomParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
public class DicomToPngServiceImpl implements DicomToPngService {

    @Autowired
    DicomParserUtil dicomParserUtil = new DicomParserUtil();

    /**
     * 讀取本地dicom檔，並將dicom檔轉換為PNG後回傳Base64字串
     * <p>
     *
     * @param dcmPath   dicom檔本                                                                                                                                                                                                                                           機端路徑
     * @param imgNumber 讀取第幾張圖片
     * @return 回傳圖片的base64字串
     */
    @Override
    public String dicomToPng(String dcmPath, Integer imgNumber) {

        File dcmFile = new File(dcmPath);
        String[] dicomPNGArray = dicomParserUtil.getDicomPngBase64(dcmFile);

        dicomParserUtil.getDicomAttribute(dcmFile);

        return dicomPNGArray[(imgNumber - 1)];
    }


    /**
     * 上傳Dicom檔，取出圖片後回傳所有圖片的base64字串。
     * <p>
     * 上傳的Dicom檔
     *
     * @param multipartFile 回傳病患基本資料與File Server的圖片URL
     * @param imgNumber 讀取第幾張圖片
     * @return 回傳圖片的base64字串
     */
    @Override
    public String dicomToPngUpload(MultipartFile multipartFile, Integer imgNumber) {


        try {
            File dcmFile = null;

            multipartFile.getContentType();

            dcmFile = File.createTempFile(multipartFile.getName(), ".DCM");

            multipartFile.transferTo(dcmFile);

            String[] dicomPNGArray = dicomParserUtil.getDicomPngBase64(dcmFile);

            dicomParserUtil.getDicomAttribute(dcmFile);

            return dicomPNGArray[(imgNumber)];

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "Dicom parsing failed !";
    }
}
