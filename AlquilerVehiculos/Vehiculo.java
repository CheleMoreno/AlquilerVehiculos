public class Vehiculo {
    public enum Tipo {PATINETE, BICICLETA, MOTO_GRANDE, MOTO}

    private Tipo tipo;
    private String nombre;
    private float bateria;
    private boolean disponible;
    private boolean averiado;
    private int cooX;
    private int cooY;
    private int viajesRealizados;

    //Se crea un constructor para crear varios vehiculos
    public Vehiculo(Tipo tipo, String nombre, float bateria, boolean disponible, boolean averiado, int cooX, int cooY, int viajesRealizados){
        this.tipo = tipo;
        this.nombre = nombre;
        this.bateria = bateria;
        this.disponible = disponible;
        this.averiado = averiado;
        this.cooX = cooX;
        this.cooY = cooY;
        this.viajesRealizados = viajesRealizados;
    }

    //Getters y setters
    public int getCooX(){return cooX;}
    public void setCooX(int cooX){this.cooX = cooX;}
    public int getCooY(){return cooY;}
    public void setCooY(int cooY){this.cooY = cooY;}

    public int getViajesRealizados(){ return viajesRealizados;}
    public void setViajesRealizados(int viaje){this.viajesRealizados = this.viajesRealizados + viaje;}

    public Tipo getTipo(){
        return tipo;
    }


    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){ this.nombre = nombre;}

    public float getBateria(){
        return bateria;
    }

    public void setBateria(float bateria) {this.bateria = Math.max(0,Math.min(100, bateria));}

    public boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }

    public boolean isAveriado(){
        return averiado;
    }

    public void setAveriado(boolean averiado){
        this.averiado = averiado;
    }

    //Mostrar la informacion de los vehiculos
    public void mostrarInfo(){
        if (this.getTipo() == Tipo.MOTO){
            System.out.println("Nombre: " + nombre + " | Tipo: " + tipo + "_PEQUENA | Bateria: " + bateria + "% | Disponible: " +(disponible ? "Si" : "No"));
        } else {
            System.out.println("Nombre: " + nombre + " | Tipo: " + tipo + " | Bateria: " + bateria + "% | Disponible: " +(disponible ? "Si" : "No"));
        }

    }

}
