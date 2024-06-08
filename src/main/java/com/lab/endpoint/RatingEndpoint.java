package com.lab.endpoint;

import com.lab.model.Car;
import com.lab.model.Rating;
import com.lab.service.RatingService;
import com.lab.utils.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


@Path("/ratings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingEndpoint {

    @Inject
    RatingService ratingService;

    @RolesAllowed({Role.ADMIN})
    @GET
    @Path("/all")
    @APIResponse(responseCode = "200", description = "List of ratings found", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = Rating.class)))
    public Response getAllRatings() {
        var response = ratingService.findAllRatings();

        if (response != null) {
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(404).build();
        }
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GET
    @Path("/user/{userId}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "List of ratings found for an user", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = Rating.class))),
            @APIResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "text/plain"))
    })
    public Response getRatingsByUserId(@PathParam("userId") Long userId) {
        var response = ratingService.findRatingsByUserId(userId);
        return Response.status(200).entity(response).build();
    }

    @RolesAllowed({Role.ADMIN})
    @GET
    @Path("/car/{carId}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "List of ratings found for a car", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = Rating.class))),
            @APIResponse(responseCode = "404", description = "Car not found", content = @Content(mediaType = "text/plain"))
    })
    public Response getRatingsByCarId(@PathParam("carId") Long carId) {
        var response = ratingService.findRatingsByCarId(carId);
        return Response.status(200).entity(response).build();
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GET
    @Path("/get/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Rating found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @APIResponse(responseCode = "404", description = "Rating not found", content = @Content(mediaType = "text/plain"))
    })
    public Response getRatingById(@PathParam("id") Long id) {
        var response = ratingService.findById(id);
        if (response != null) {
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(404).build();
        }
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @POST
    @Path("/create")
    public Response insert(@QueryParam("bookingId") Long bookingId, @QueryParam("carId") Long carId, @QueryParam("userId") Long userId, Rating rating) {
        ratingService.insertRating(bookingId, userId, carId, rating);
        return Response.status(201).build();
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @PUT
    @Path("/update")
    public Response update(Rating rating) {
        ratingService.updateRating(rating);
        return Response.status(201).build();
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        ratingService.deleteRating(id);
        return Response.status(201).build();
    }
}
