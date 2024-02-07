package com.example.demo.service.util;


import org.apache.axis.encoding.Base64;
import org.apache.axis.utils.ByteArrayOutputStream;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.SafeClose;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;


@Component
public class DicomParserUtil {


    private final String formatName = "png";

    /**
     * 讀取本地dicom檔，並將dicom檔內的圖片取出轉換為PNG後以Array格式回傳所有圖片的Base64字串
     * <p>
     *@param dcmFile 需要解析的dicom檔
     * @return 回傳dicom檔案裡所有楨的圖片的base64字串
     */
    public String[] getDicomPngBase64(File dcmFile) {


        ImageInputStream iis = null;

        BufferedImage bi;

        OutputStream out = null;

        ImageReader imageReader = ImageIO.getImageReadersByFormatName("DICOM").next();


        try {

            iis = ImageIO.createImageInputStream(dcmFile);

            imageReader.setInput(iis, true);

            String[] picBase64Array = new String[imageReader.getNumImages(true)];

            for (int i = 0; i < imageReader.getNumImages(true); i++) {

                bi = imageReader.read(i);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                ImageIO.write(bi, formatName, stream);

                picBase64Array[i] = Base64.encode(stream.toByteArray());

            }
            return picBase64Array;
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            SafeClose.close(iis);
            SafeClose.close(out);

        }

        return null;

    }

    /**
     * 讀取本地dicom檔，並將dicom檔內的圖片取出轉換為PNG後以Array格式回傳所有圖片的Base64字串
     * <p>
     *@param dcmFile 需要解析的dicom檔
     * @return 回傳dicom檔案的Attribute (即dicom檔除了圖片外其他的資訊，包含Patient name、dicom檔的楨數等等)
     */
    public String getDicomAttribute(File dcmFile) {

        DicomInputStream in = null;

        Attributes attr = null;

        try {
            in = new DicomInputStream(dcmFile);

            attr = in.readDataset(-1, -1);

        } catch (Exception e) {

            e.printStackTrace();
        }

//     取出Patient name
        System.out.println("Patient Name : " + attr.getString(Tag.PatientName));

//     Dicom的楨數，即Dicom內有多少張影像
        System.out.println("Dicom Number Of Frame : " + attr.getString(Tag.NumberOfFrames));

//      回傳Attribute字串
        return attr.toString();
    }


}
