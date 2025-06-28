import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Trabajadores extends Persona{
    //Creamos listas para vehiculos y bases asignadas
    private String tipo;
    private List<Vehiculo> vehiculosAsignados = new ArrayList<>();
    private List<Base> basesAignadas = new ArrayList<>();
    private List<String> facturas = new ArrayList<>();
    protected boolean activo = true;

    //Constructor para crear Trabajadores
    Trabajadores(String nombre, String apellido, String email, int dni, int telefono, String tipo){
        super(nombre, apellido, email, dni, telefono);
        this.tipo = tipo.toLowerCase();
    }

    public boolean isActivo(){return activo;}
    public void setActivo(boolean activo){ this.activo = activo;}
    public String getTipo(){
        return tipo;
    }

    //Agregar los vehiculos o bases asignadas a trabajadores
    public void asignarVehiculo(Vehiculo v){
        vehiculosAsignados.add(v);
    }
    public void asignarBase(Base b){
        basesAignadas.add(b);
    }

    //Loop para el menu
    public void mostrarMenuTrabajadores(Scanner scanner){
        boolean menuUsuario = true;
        while (menuUsuario) {
            //Muestra el menu de mecanico o mantenimiento, segun el tipo de trabajador
            switch (tipo) {
                case "mecanico":
                    System.out.println("\n--- BIENVENIDO AL MENU DE MECANICOS ---");
                    System.out.println("1. Vehiculos y bases para reparacion");
                    System.out.println("2. Historial de facturas");
                    System.out.println("0. Cerrar sesion\n");
                    int opcionMecanico = scanner.nextInt();
                    scanner.nextLine();
                    switch(opcionMecanico){
                        case 1:
                            System.out.println("\n--- VISUALIZACION DE VEHICULOS Y BASES ASIGNADOS PARA REPARACION");
                            repararElementos(scanner);
                            break;

                        case 2:
                            System.out.println("\n--- FACTURAS");
                            mostrarFacturas();
                            break;

                        case 0:
                            System.out.println("\n\nHasta luego...");
                            menuUsuario = false;
                            break;
                    }
                        break;
                case "mantenimiento":
                    System.out.println("\n--- BIENVENIDO AL MENU DE MANTENIMIENTO ---");
                    System.out.println("1. Vehiculos asignados para mantenimiento y recogida");
                    System.out.println("0. Cerrar sesion\n");

                    int opcionMantenimiento = scanner.nextInt();
                    scanner.nextLine();
                    switch (opcionMantenimiento){
                        case 1:
                            recogerVehiculo(scanner);
                            break;
                        case 0:
                            menuUsuario = false;
                            break;
                    }
                    break;

                default:
                    System.out.println("\nTipo de trabajador desconocido");

            }

        }
    }

    //Metodo solo para trabajadores de mantenimiento, que recogen el vehiculo y lo desabilitan
    public void recogerVehiculo(Scanner scanner){
        //Verifica si tiene vehiculos asignados por el administrador
        if (vehiculosAsignados.isEmpty()){
            System.out.println("No hay vehiculos asignados\n");
            return;
        }

        System.out.println("\n--- VEHICULOS ASIGNADOS ---");
        for (int i = 0; i < vehiculosAsignados.size(); i++){
            Vehiculo v = vehiculosAsignados.get(i);
            System.out.println((i + 1) + ". " + v.getNombre() + " (" + v.getTipo() + ")");
        }

        System.out.println("\nSeleccione el vehiculo a recoger: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion < 1 || opcion > vehiculosAsignados.size()){
            System.out.println("Opcion invalida.\n");
        }

        //Recoge el vehiculo y lo quita de la lista (si aun seguia como disponible)
        Vehiculo seleccionado = vehiculosAsignados.remove(opcion - 1);
        seleccionado.setDisponible(false);
        System.out.println("\n--- Vehiculo " + seleccionado.getNombre() + " recogido para su reparacion.");
    }

    //Metodo solo para mecanicos, donde se cambia el valor de averiado a falso, se pone como disponible y se carga al 100 la bateria
    public void repararElementos(Scanner scanner){
        if (vehiculosAsignados.isEmpty() && basesAignadas.isEmpty()){
            System.out.println("\n---No hay vehiculos ni bases para reparar :)\n");
            return;
        }

        System.out.println("\n--- VEHICULOS Y BASES: ");
        int index = 1;
        List<Object> reparables = new ArrayList<>();

        for (Vehiculo v : vehiculosAsignados){
            System.out.println(index + ". Vehiculo: " + v.getNombre() + " (" + v.getTipo() + ")");
            reparables.add(v);
            index++;
        }

        for (Base b : basesAignadas){
            System.out.println(index + ". Base: " + b.getNombre());
            reparables.add(b);
            index++;
        }

        System.out.println("Seleccione un elemento a reparar:");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion < 1 || opcion > reparables.size()){
            System.out.println("Opcion invalida");
            return;
        }

        Object seleccionado = reparables.get(opcion - 1);

        //Verifica si es un vehiculo o una base
        if (seleccionado instanceof Vehiculo){
            Vehiculo v = (Vehiculo) seleccionado;
            v.setAveriado(false);
            v.setDisponible(true);
            v.setBateria(100);
            //Se quita el vehiculo de la lista de asignados y se genera una factura
            vehiculosAsignados.remove(v);
            facturas.add("Factura: Reparacion de " + v.getNombre() + " - 20€");
            System.out.println("\n-- Vehiculo " + v.getNombre() + " reparado");
        } else if (seleccionado instanceof Base){
            Base b = (Base) seleccionado;
            b.setAveriada(false);
            //Se quita la base de la lista de asignadas y se genera factura
            basesAignadas.remove(b);
            facturas.add("Factura: Reparacion de " + b.getNombre() + " - 50€");
            System.out.println("\n-- Base " + b.getNombre() + " reparada");
        }
    }

    //Se muestran las facturas que han generado los mecanicos
    private void mostrarFacturas(){
        if (facturas.isEmpty()){
            System.out.println("No hay facturas generadas");
            return;
        }

        System.out.println("\n--- FACTURAS ---");
        for (String f : facturas){
            System.out.println(f);
        }
    }

}
