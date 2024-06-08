package com.lab.endpoint;

import com.lab.model.Booking;
import com.lab.service.BookingService;
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


@Path("/bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingEndpoint {

    @Inject
    BookingService bookingService;

    @RolesAllowed({Role.ADMIN})
    @GET
    @Path("/all")
    @APIResponse(responseCode = "200", description = "List of bookings found", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = Booking.class)))
    public Response getAllBookings() {
        var response = bookingService.findAllBookings();
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
            @APIResponse(responseCode = "200", description = "List of bookings found for an user", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = Booking.class))),
            @APIResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "text/plain"))
    })
    public Response getBookingsByUserId(@PathParam("userId") Long userId) {
        var response = bookingService.findBookingsByUserId(userId);
        if (response != null) {
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(404).build();
        }
    }

    @RolesAllowed({Role.ADMIN})
    @GET
    @Path("/car/{carId}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "List of bookings found for a car", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = Booking.class))),
            @APIResponse(responseCode = "404", description = "Car not found", content = @Content(mediaType = "text/plain"))
    })
    public Response getBookingsByCarId(@PathParam("carId") Long carId) {
        var response = bookingService.findBookingsByCarId(carId);

        if (response != null) {
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(404).build();
        }
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @POST
    @Path("/create")
    public Response insert(@QueryParam("carId") Long carId, @QueryParam("userId") Long userId, Booking booking) {
        bookingService.insertBooking(userId, carId, booking);
        return Response.status(201).build();
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @PUT
    @Path("/update")
    public Response update(Booking booking) {
        bookingService.updateBooking(booking);
        return Response.status(201).build();
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        bookingService.deleteBooking(id);
        return Response.status(201).build();
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GET
    @Path("/get/{id}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Booking found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))),
            @APIResponse(responseCode = "404", description = "Booking not found", content = @Content(mediaType = "text/plain"))
    })
    public Response getBookingById(@PathParam("id") Long id) {
        var response = bookingService.getBookingById(id);
        if (response != null) {
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(404).build();
        }
    }
}
