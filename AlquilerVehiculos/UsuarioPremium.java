import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class UsuarioPremium extends Usuario{
    private List<Reserva> reservas = new ArrayList<>();

    UsuarioPremium(String nombre, String apellido, String email, int dni, int telefono, Double saldo) {
        super(nombre, apellido, email, dni, telefono, saldo);
        this.tipoUsuario = TipoUsuario.PREMIUM;
    }

    public void agregarReserva(Reserva reserva){
        reservas.add(reserva);
    }

    public List<Reserva> getReservas(){
        return reservas;
    }

    //Metodo unico para usuarios PREMIUM, donde pueden reservar un vehiculo con 20 mins de anticipacion
    public void reservarVehiculo(Scanner scanner, SistemaVehiculos sistema){
        System.out.println("\n--- RESERVA DE VEHICULOS (20 MINUTOS) ---");

        //Primero mostramos y agregamos todos los vehiculos disponibles en una nueva lista
        List<Vehiculo> disponibles = new ArrayList<>();
        Vehiculo.Tipo tipoAnterior = null;
        int i = 1;

        for (Vehiculo v : sistema.getMotos()){
            if (v.isDisponible() && !v.isAveriado()){
                if (tipoAnterior != null && tipoAnterior != v.getTipo()){
                    System.out.println();
                }
                System.out.println(i + ". " + v.getNombre() + " |Tipo: " + v.getTipo() + " | Bateria: " + v.getBateria() + "%");
                disponibles.add(v);
                i++;
                tipoAnterior = v.getTipo();
            }
        }

        for (Base b : sistema.getBases()){
            if (b.isAveriada()) continue;
            for (Vehiculo v : b.getVehiculos()){
                if (v.isDisponible() && !v.isAveriado()){
                    if (tipoAnterior != null && tipoAnterior != v.getTipo()){
                        System.out.println();
                    }
                    System.out.println(i + ". [" + b.getNombre() + "] " + v.getNombre() + " | Bateria: " + v.getBateria());
                    disponibles.add(v);
                    i++;
                    tipoAnterior = v.getTipo();
                }
            }
        }

        if (disponibles.isEmpty()){
            System.out.println("\nNo hay vehiculos disponibles para reservar");
            return;
        }

        //Seleccionamos el vehiculo y le cambiamos sus disponibilidad para que no salga en las listas
        System.out.println("\nSeleccione el vehiculo a reservar:");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion < 1 || opcion > disponibles.size()){
            System.out.println("\nOpcion invalida");
            return;
        }

        Vehiculo elegido = disponibles.get(opcion - 1);
        elegido.setDisponible(false);

        //Creamos un objeto nuevo Reserva
        Reserva reserva = new Reserva(elegido, LocalDateTime.now());
        this.agregarReserva(reserva);

        System.out.println("\nVehiculo: "+ elegido.getNombre()+ " reservado exitosamente por 20 minutos");
    }

    //Verifica si la reserva esta activa (si pasaron los 20 minutos o si se inicio un viaje) y la remueve de la lista de reservas
    public void verificarReservasExpiradas(){
        Iterator<Reserva> it = reservas.iterator();
        while (it.hasNext()){
            Reserva r = it.next();
            if (!r.estaActiva()){
                r.getVehiculo().setDisponible(true);
                it.remove();
            }
        }
    }

    //Iniciamos un viaje con el vehiculo reservado
    public void iniciarViajeConReserva(Scanner scanner, SistemaVehiculos sistema){
        verificarReservasExpiradas();

        //Verificamos que existan vehiculos en la lista
        if (reservas.isEmpty()){
            System.out.println("\nNo tiene reservas activas");
            return;
        }

        //Mostramos vehiculos y hora de expiracion
        System.out.println("\n--- VEHICULOS RESERVADOS ACTIVOS ---");
        for (int i = 0; i < reservas.size(); i++){
            Reserva r = reservas.get(i);
            System.out.println((i + 1) + ". " + r.getVehiculo().getNombre() + " (" + r.getVehiculo().getTipo() + ") | Expira a las " + r.getExpiracion().toLocalTime());
        }

        //Seleccion de vehiculo y validacion
        System.out.println("\nSeleccione un vehiculo para iniciar el viaje");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion < 1 || opcion > reservas.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        //Apuntamos al vehiculo y cambiamos su disponibilidad para que no se pueda utilizar
        Reserva seleccionada = reservas.remove(opcion - 1);
        Vehiculo v = seleccionada.getVehiculo();
        v.setDisponible(false);
        String baseInicio = obtenerOrigenVehiculo(v, sistema);

        this.viaje.iniciarViaje(this, v, baseInicio , scanner);
    }

    //Metodo para saber si el vehiculo inicia de una base (BICICLETAS o PATINETES) o si desde una coordenada (MOTOS)
    private String obtenerOrigenVehiculo(Vehiculo vehiculo, SistemaVehiculos sistema){
        for (Base b : sistema.getBases()){
            if (b.getVehiculos().contains(vehiculo)){
                return b.getNombre();
            }
        }
        return "Ubicacion [X:" + vehiculo.getCooX() + ", Y:" + vehiculo.getCooY() + "]";
    }
}
