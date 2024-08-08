package ec.com.bienesracies.models;

public class Propiedades {
    private int id;
    private String direccion;
    private String descripcion;
    private String precio;
    private int id_agente;

    // Constructor vacío
    public Propiedades() {}

    // Constructor con parámetros

    public Propiedades(int id, String direccion, String descripcion, String precio, int id_agente) {
        this.id = id;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id_agente = id_agente;
    }
    public Propiedades(String direccion,String descripcion, String precio, int id_agente) {
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id_agente = id_agente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Getter y setter para 'descripcion'
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getter y setter para 'precio'
    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    // Getter y setter para 'id_usuario'
    public int getId_agente() {
        return id_agente;
    }

    public void setId_agente(int id_agente) {
        this.id_agente = id_agente;
    }
}
