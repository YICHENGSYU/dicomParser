package com.example.demo.controller;

import com.example.demo.service.DicomToPngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dicomToPngParser/")
public class DicomToPngController {

    @Autowired
    private DicomToPngService dicomToPngService;


    @PostMapping(value="/dicomToPng", produces = "application/json")
    public String dicomToPng(@RequestParam("dcmPath") String  dcmPath, @RequestParam("imgNumber")  Integer imgNumber) {

        return  dicomToPngService.dicomToPng(dcmPath,imgNumber);

    }


    @PostMapping(value="/dicomToPngUpload")
    public String dicomToPngUpload(@RequestParam("dcmFile") MultipartFile multipartFile ,@RequestParam Integer imgNumber) {

        return  dicomToPngService.dicomToPngUpload(multipartFile,imgNumber);

    }

}
