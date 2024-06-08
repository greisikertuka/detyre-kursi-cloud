package com.lab.endpoint;

import com.lab.model.ImageUploadForm;
import com.lab.service.FileService;
import com.lab.utils.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/files")
@ApplicationScoped
public class FileEndpoint {

    @Inject
    FileService fileService;

    @GET
    @Path("/users/{userId}/profile-picture")
    @RolesAllowed({Role.USER, Role.ADMIN})
    @Produces("image/jpeg")
    public Response getUserProfilePicture(@PathParam("userId") String userId) {
        return fileService.getUserProfilePicture(userId);
    }

    @POST
    @Path("/users/{userId}/profile-picture")
    @RolesAllowed({Role.USER, Role.ADMIN})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadUserProfileImage(@PathParam("userId") String userId, @MultipartForm ImageUploadForm form) {
        return fileService.saveUserProfilePicture(form, userId);
    }

    @GET
    @Path("/cars/{carId}/thumbnail")
    @RolesAllowed({Role.USER, Role.ADMIN})
    @Produces("image/jpeg")
    public Response getCarThumbnail(@PathParam("carId") String carId) {
        return fileService.getCarThumbnail(carId);
    }

    @POST
    @Path("/cars/{carId}/thumbnail")
    @RolesAllowed({Role.ADMIN})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadCarImage(@PathParam("carId") String carId, @MultipartForm ImageUploadForm form) {
        return fileService.saveCarPicture(form, carId);
    }

    @POST
    @Path("/cars/{carId}/photos")
    @RolesAllowed({Role.ADMIN})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleFileUploadForm(@PathParam("carId") String carId, @MultipartForm MultipartFormDataInput input) {
        return fileService.uploadCarImages(input, carId);
    }
}
