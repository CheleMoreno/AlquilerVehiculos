import java.util.ArrayList;
import java.util.List;

public class Base {
    private String nombre;
    private ArrayList<Vehiculo> vehiculos;
    private boolean averiada = false;

    //Se crea constructor
    public Base(String nombre){
        this.nombre = nombre;
        this.vehiculos = new ArrayList<>();
    }

    public String getNombre(){
        return nombre;
    }

    //Agregar un vehiculo a la base, si no existe
    public void agregarVehiculo(Vehiculo v){
        if (!vehiculos.contains(v)) {
            vehiculos.add(v);
        }
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    /*
    //Mostrar vehiculos disponibles
    public void mostrarVehiculosDisponibles(){
        for (Vehiculo v : vehiculos){
            if (v.isDisponible() && v.getBateria() > 20 && !v.isAveriado()){
                v.mostrarInfo();
            }
        }
    }

     */

    public boolean tieneEspacio(){
        return vehiculos.size() < 20;
    }

    public boolean isAveriada(){
        return averiada;
    }

    public void setAveriada(boolean averiada){
        this.averiada = averiada;
    }

}
