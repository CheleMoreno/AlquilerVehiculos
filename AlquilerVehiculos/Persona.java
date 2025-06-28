import java.util.List;

public class Persona {

    String nombre;
    String apellido;
    String email;
    int dni;
    int telefono;


    //Constructor para crear distintos usuarios
    Persona(String nombre, String apellido, String email, int dni, int telefono){
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
        this.telefono = telefono;
    }

    //Getters y setters
    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getDni(){
        return dni;
    }

    public void setDni(int dni){
        this.dni = dni;
    }

    public int getTelefono(){
        return telefono;
    }

    public void setTelefono(int telefono){
        this.telefono = telefono;
    }

    //Forma mas simple de buscar por ID
    /*//Buscar por ID
    public static Usuario buscarUsuario(List<Usuario> listaUsuarios, int id){
        for (Usuario usuario : listaUsuarios){
            if (usuario.getDni() == id){
                return usuario;
            }
        }
        return null;
    }

     */


}
