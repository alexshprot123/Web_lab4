import DTO.AddDoctorDTO;
import DTO.AddNullSlotDTO;
import DTO.AddUserDTO;
import DTO.AddWrittenSlotDTO;
import Entity.DoctorEntity;
import Entity.SlotEntity;
import Entity.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("")
public class MedAPI {

    @GET
    @Path("/doctors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DoctorEntity> getAllDoctors() {
        return MedController.getAllDoctors();
    }

    @POST
    @Path("/doctors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDoctor(AddDoctorDTO addDoctorDTO) {
        MedController.addDoctor(addDoctorDTO);
        return Response.ok("Доктор успешно добавлен").build();
    }

    @DELETE
    @Path("/doctors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDoctor(@PathParam("id")int id) {
        MedController.deleteDoctor(id);
        return Response.ok("Доктор успешно удалён").build();
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getAllUsers() {
        return MedController.getAllUsers();
    }

    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(AddUserDTO addUserDTO) {
        MedController.addUser(addUserDTO);
        return Response.ok("Пользователь успешно добавлен").build();
    }

    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id")int id) {
        MedController.deleteUser(id);
        return Response.ok("Пользователь успешно удалён").build();
    }

    @GET
    @Path("/slots")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SlotEntity> getAllSlots() {
        return MedController.getAllSlots();
    }

    @POST
    @Path("/slots/write")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSlot(AddWrittenSlotDTO addSlotDTO) {
        if(MedController.addWrittenSlot(addSlotDTO))
            return Response.ok("Запись прошла успешно").build();
        return Response.status(Response.Status.FORBIDDEN).entity("Такого слота не существует").build();
    }

    @POST
    @Path("/slots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSlot(AddNullSlotDTO addNullSlotDTO) {
        MedController.addNullSlot(addNullSlotDTO);
        return Response.ok("Слот успешно добавлен").build();
    }
}
