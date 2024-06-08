package com.lab.model;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;


public class ImageUploadForm {
    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] data;
}