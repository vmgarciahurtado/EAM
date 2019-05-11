package com.example.victor.eam.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/registroEstudiantes.php")
    public void insertUser(
            @Field("codigo") String codigo,
            @Field("cedula") String cedula,
            @Field("nombre") String nombre,
            @Field("fechaNacimiento") String fechaNacimiento,
            @Field("estado") String estado,
            @Field("direccion") String direccion,
            @Field("telefono") String telefono,
            @Field("correo") String correo,
            @Field("programaAcademico") String programaAcademico,
            Callback<Response> callback
    );
}
