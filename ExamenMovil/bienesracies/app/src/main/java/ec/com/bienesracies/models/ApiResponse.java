package ec.com.bienesracies.models;

public class ApiResponse {
    private boolean success;
    private String status;
    private String message;
    private Propiedades propiedades;

    // Getters y setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Propiedades getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Propiedades propiedades) {
        this.propiedades = propiedades;
    }

}
