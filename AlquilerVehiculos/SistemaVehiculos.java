import java.util.ArrayList;
import java.util.List;

public class SistemaVehiculos {
    private ArrayList<Vehiculo> motos;
    private ArrayList<Base> bases;
    private Ciudad ciudad;
    //Para crear las conexiones en BlueJ
    private Vehiculo dummyVehiculo;
    private Base dummyBase;

    public SistemaVehiculos() {
        //Creamos una lista para motos y otra para bases
        motos = new ArrayList<>();
        bases = new ArrayList<>();

        //Limites de la ciudad
        ciudad = new Ciudad(0, 100, 0, 100);

        //Agregamos motos
        // Motos grandes
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO_GRANDE, "Moto Grande 1", 100, true, false, 10, 20, 19));
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO_GRANDE, "Moto Grande 2", 95, true, false, 15, 49, 21));
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO_GRANDE, "Moto Grande 3", 85, true, false, 12, 30, 17));
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO_GRANDE, "Moto Grande 4", 90, true, false, 18, 35, 25));

        // Motos pequeñas
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO, "Moto Pequena 1", 100, true, false, 8, 18, 23));
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO, "Moto Pequena 2", 90, true, false, 13, 22, 19));
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO, "Moto Pequena 3", 95, true, false, 17, 27, 24));
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO, "Moto Pequena 4", 88, true, false, 20, 32, 18));
        motos.add(new Vehiculo(Vehiculo.Tipo.MOTO, "Moto Pequena 5", 92, true, false, 14, 29, 21));

        //Agregamos bases
        // Base Norte
        Base baseNorte = new Base("Base Norte");

        // Patinetes Base Norte
        baseNorte.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Norte 1", 95, true, false, 40, 21, 23));
        baseNorte.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Norte 2", 100, true, false, 40, 22, 24));
        baseNorte.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Norte 3", 85, true, false, 41, 23, 18));

        // Bicicletas Base Norte
        baseNorte.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Norte 1", 92, true, false, 40, 25, 12));
        baseNorte.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Norte 2", 88, true, false, 41, 26, 15));
        baseNorte.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Norte 3", 79, true, false, 42, 27, 14));
        baseNorte.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Norte 4", 84, true, false, 43, 28, 19));

        // Base Sur
        Base baseSur = new Base("Base Sur");

        // Patinetes Base Sur
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Sur 1", 88, true, false, 10, 10, 15));
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Sur 2", 92, true, false, 11, 11, 18));
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Sur 3", 97, true, false, 12, 12, 14));
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Sur 4", 83, true, false, 13, 13, 19));
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Sur 5", 79, true, false, 14, 14, 16));

        // Bicicletas Base Sur
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Sur 1", 96, true, false, 10, 16, 13));
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Sur 2", 86, true, false, 11, 17, 16));
        baseSur.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Sur 3", 90, true, false, 12, 18, 14));

        // Base Este
        Base baseEste = new Base("Base Este");

        // Patinetes Base Este
        baseEste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Este 1", 10, false, true, 50, 20, 23));
        baseEste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Este 2", 15, false, true, 51, 21, 18));

        // Bicicletas Base Este
        baseEste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Este 1", 12, false, true, 50, 43, 1));
        baseEste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Este 2", 7, false, true, 51, 44, 3));
        baseEste.setAveriada(true);

        // Base Oeste
        Base baseOeste = new Base("Base Oeste");

        // Patinetes Base Oeste
        baseOeste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Oeste 1", 86, true, false, 5, 50, 14));
        baseOeste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.PATINETE, "Patinete Oeste 2", 91, true, false, 6, 51, 18));

        // Bicicletas Base Oeste
        baseOeste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Oeste 1", 93, true, false, 5, 56, 15));
        baseOeste.agregarVehiculo(new Vehiculo(Vehiculo.Tipo.BICICLETA, "Bici Oeste 2", 88, true, false, 6, 57, 18));

        // Añadir todas las bases a la lista
        bases.add(baseNorte);
        bases.add(baseSur);
        bases.add(baseEste);
        bases.add(baseOeste);
    }

    public ArrayList<Vehiculo> getMotos(){
        return motos;
    }

    public ArrayList<Base> getBases(){
        return bases;
    }

    public Ciudad getCiudad(){ return ciudad;}

    //Mostramos las motos disponibles
    public void mostrarMotosDisponibles(){
        System.out.println("\n--- MOTOS DISPONIBLES: \n-------------------------------------------------------------------------------");
        Vehiculo.Tipo tipoAnterior = null;

        for (Vehiculo v : motos) {

            if (motos.isEmpty()){
                System.out.println("\nNo hay motos disponibes");

            } else {
                if (v.isDisponible() && v.getBateria() > 20 && !v.isAveriado()){
                    if (tipoAnterior != null && tipoAnterior != v.getTipo()){
                        System.out.println();
                    }
                    v.mostrarInfo();
                    tipoAnterior = v.getTipo();
                }
            }
        }
    }
    //Mostramos las bases disponibles
    public void mostrarBasesConVehiculos(){
        for (Base b : bases){
            if (b.isAveriada()) continue;

            List<Vehiculo> disponibles = new ArrayList<>();
            for (Vehiculo v : b.getVehiculos()){
                if (v.isDisponible() && v.getBateria() > 20 && !v.isAveriado()){
                    disponibles.add(v);
                }
            }

            System.out.println("\n---BASE: " + b.getNombre() + "\n-------------------------------------------------------------------------------");

            if (disponibles.isEmpty()){
                System.out.println("\nNo hay vehiculos disponibles en esta base.");
            } else {
                Vehiculo.Tipo tipoAnterior = null;

                for (Vehiculo v : disponibles){
                    if (tipoAnterior != null && tipoAnterior != v.getTipo()){
                        System.out.println();
                    }
                    v.mostrarInfo();
                    tipoAnterior = v.getTipo();
                }
            }
        }
    }

    //Quitamos el vehiculo de una base al finalizar un viaje o para su reparacion
    public void removerVehiculoDeBase(Vehiculo vehiculo){
        for (Base b : bases){
            if (b.getVehiculos().contains(vehiculo)){
                b.getVehiculos().remove(vehiculo);
                return;
            }
        }
    }
}