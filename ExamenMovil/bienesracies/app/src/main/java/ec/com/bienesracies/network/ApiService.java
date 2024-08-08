package ec.com.bienesracies.network;

import ec.com.bienesracies.models.Propiedades;
import ec.com.bienesracies.models.ApiResponse;
import ec.com.bienesracies.models.EliminarPropiedadRequest;
import ec.com.bienesracies.models.DetallesPropiedadRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/crearPropiedad")
    Call<Propiedades> createPropiedad(@Body Propiedades propiedades);

    @POST("/api/listarPropiedad")
    Call<List<Propiedades>> listaPropiedad();

    @POST("/api/detallesPropiedad")
    Call<Propiedades> detallesPropiedad(@Body Map<String, Integer> body);

    @POST("/api/actualizarPropiedad")
    Call<Propiedades> actualizarPropiedad(@Body Map<String, Object> body);

    @POST("/api/eliminarPropiedad")
    Call<ApiResponse> eliminarPropiedad(@Body Map<String, Integer> id);
}

