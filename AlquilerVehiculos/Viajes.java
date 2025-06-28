import java.util.Scanner;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Viajes {
    private Vehiculo vehiculoActual;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String baseInicio;
    private String baseFin;
    private double coste;
    private List<String> historialViajes;

    public Viajes(){
        this.historialViajes = new ArrayList<>();
    }

    //Se inicia con viaje y se le pasa un usuario, vehiculo, base de incio y el scanner
    public boolean iniciarViaje(Usuario usuario, Vehiculo vehiculo, String baseInicio, Scanner scanner){
        //Chequeo de saldo y que el usuario este de alta para realizar un viaje
        if (usuario.getSaldo() <= 0){
            System.out.println("\nNo tiene saldo suficiente para iniciar un viaje.");
            return false;
        }
        if (!usuario.isActivo()){
            System.out.println("El usuario no esta activo. Hable con el administrador");
            return false;
        }

        //Chequeo de bateria, segun usuario
        float bateriaMinima = (usuario.getTipoUsuario() == Usuario.TipoUsuario.PREMIUM) ? 10.0f : 20.0f;
        if (vehiculo.getBateria() < bateriaMinima){
            System.out.println("\nEl vehiculo no tiene bateria suficiente para iniciar un viaje.");
            return false;
        }

        //Iniciar el viaje con la hora local
        this.vehiculoActual = vehiculo;
        this.horaInicio = LocalTime.now();
        this.baseInicio = baseInicio;
        vehiculo.setDisponible(false);

        System.out.println("\nViaje iniciado con " + vehiculo.getNombre() + " a las " + horaInicio.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return true;
    }

    //Se finaliza el viaje iniciado anteriormente
    public void finalizarViaje(Usuario usuario, String baseFin, SistemaVehiculos sistema, Scanner scanner){
        //Se verifica que haya un viaje en progreso
        if (vehiculoActual == null){
            System.out.println("No hay ningun viaje en curso.");
            return;
        }
        //Se suma un viaje al vehiculo actual
        vehiculoActual.setViajesRealizados(1);

        //Finalizar el viaje con la hora local y guarda la base
        this.horaFin = LocalTime.now();
        this.baseFin = baseFin;

        //Calcular duracion del viaje en minutos
        long duracionMinutos = Duration.between(horaInicio, horaFin).toMinutes();
        if (duracionMinutos < 1) duracionMinutos = 1; //Minimo de un minuto

        //Calcular consumo de bateria
        float consumoPorMinuto = 0;
        switch (vehiculoActual.getTipo()){
            case BICICLETA:
                consumoPorMinuto = 1.0f;
                break;
            case PATINETE:
                consumoPorMinuto = 0.5f;
                break;
            case MOTO:
                consumoPorMinuto = 0.4f;
                break;
            case MOTO_GRANDE:
                consumoPorMinuto = 0.25f;
                break;
        }

        //Calcular el consumo total de bateria y calcular penalizacion
        float consumoTotal = consumoPorMinuto * duracionMinutos;
        float bateriaNueva = vehiculoActual.getBateria() - consumoTotal;

        //Penlizacion si la bateria se acaba
        double penalizacion = 0;
        if (bateriaNueva <= 0) {
            bateriaNueva = 0;
            penalizacion = 1.0;
            System.out.println("\nLa bateria se agoto antes de llegar al destino. Se aplicara una penalizacion de 1 euro.");
        }

        //Actualizar bateria del vehiculo
        vehiculoActual.setBateria(bateriaNueva);

        //Calcular coste
        double costePorMinuto = 0;
        switch (vehiculoActual.getTipo()){
            case BICICLETA:
                costePorMinuto = (usuario.getTipoUsuario() == Usuario.TipoUsuario.PREMIUM) ? Tarifas.biciPremium : Tarifas.biciStandard;
                break;
            case PATINETE:
                costePorMinuto = (usuario.getTipoUsuario() == Usuario.TipoUsuario.PREMIUM) ? Tarifas.patinPremium : Tarifas.patinStandard;
                break;
            case MOTO:
                costePorMinuto = (usuario.getTipoUsuario() == Usuario.TipoUsuario.PREMIUM) ? Tarifas.motoPremium : Tarifas.motoStandard;
                break;
            case MOTO_GRANDE:
                costePorMinuto = (usuario.getTipoUsuario() == Usuario.TipoUsuario.PREMIUM) ? Tarifas.motoGrandePremium : Tarifas.motoGrandeStandard;
                break;
        }
        this.coste = costePorMinuto * duracionMinutos + penalizacion;

        //Actualizar el saldo
        usuario.reducirSaldo(this.coste);

        //Devolver vehiculo a disponible
        vehiculoActual.setDisponible(true);

        //Registrar el viaje en el historial
        String registro = String.format(
                "Viaje %s -> %s | Vehiculo: %s | Duracion: %d min | Bateria final: %.1f%% | Coste: %.2f€",
                baseInicio, baseFin, vehiculoActual.getNombre(), duracionMinutos, bateriaNueva, coste);
        historialViajes.add(registro);

        System.out.println("\nViaje finalizado con " + vehiculoActual.getNombre() + " a las " + horaFin.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\nCon un coste de: " + String.format("%.2f", coste) + "€");

        //Liberar vehiculo actual
        vehiculoActual = null;


    }

    //Muestra todos los viajes realizados por el usuario
    public void mostrarHistorial(){
        if (historialViajes.isEmpty()){
            System.out.println("No hay viajes registrados en el historial.");
            return;
        }

        System.out.println("\n--- HISTORIAL DE VIAJES ---");
        for (String viaje : historialViajes){
            System.out.println(viaje);
        }
    }


    //Loop para el menu de viajes
    public void menuViajes(Usuario usuario, SistemaVehiculos sistema, Scanner scanner){
        boolean menuActivo = true;

        while (menuActivo){
            System.out.println("\n--- MENU DE VIAJES ---");
            System.out.println("1. Iniciar viaje");
            System.out.println("2. Finalizar viaje");
            //System.out.println("3. Ver historial de viajes");
            System.out.println("0. Salir");
            System.out.println("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                //Se inicia o se finaliza un viaje con sus validaciones
                case 1:
                    if (vehiculoActual != null){
                        System.out.println("Ya tiene un viaje en curso. Finalice el viaje actual antes de empezar uno nuevo.");
                        break;
                    }
                    iniciarNuevoViaje(usuario, sistema, scanner);
                    break;

                case 2:
                    if (vehiculoActual == null){
                        System.out.println("No hay ningun viaje en curso para finalizar.");
                        break;
                    }
                    finalizarViajeActual(usuario, sistema, scanner);
                    menuActivo = false;
                    break;

                /*case 3:
                    mostrarHistorial();
                    break;
                 */

                case 0:
                    //Sale del loop
                    menuActivo = false;
                    break;

                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }
    }

    //Metodo para que el usuario seleccione el tipo de vehiculo para iniciar su viaje
    private void iniciarNuevoViaje(Usuario usuario, SistemaVehiculos sistema, Scanner scanner){
        System.out.println("\nSeleccione el tipo de vehiculo:\n-----------------------------------");
        System.out.println("1. Patinete/Bicicleta (desde base)");
        System.out.println("2. Motos");

        int tipoVehiculo = scanner.nextInt();
        scanner.nextLine();

        if (tipoVehiculo == 1){
            //Mostrar bases disponibles
            List<Base> basesDisponibles = new ArrayList<>();
            for (Base b : sistema.getBases()){
                if (!b.isAveriada()){
                    basesDisponibles.add(b);
                }
            }

            if (basesDisponibles.isEmpty()){
                System.out.println("\nNo hay bases disponibles para alquilar");
                return;
            }


            System.out.println("\nBases disponibles:");
            for (int i = 0; i < basesDisponibles.size(); i++){
                System.out.println((i+1) + ". " + basesDisponibles.get(i).getNombre());
            }

            System.out.println("\nSeleccione una base: ");
            int baseSeleccionada = scanner.nextInt();
            scanner.nextLine();

            if (baseSeleccionada < 1 || baseSeleccionada > basesDisponibles.size()){
                System.out.println("\nOpcion invalida.");
                return;
            }

            Base base = basesDisponibles.get(baseSeleccionada - 1);
            ArrayList<Vehiculo> vehiculosDisponibles = base.getVehiculos();

            System.out.println("\nVehiculos disponibles en " +base.getNombre() + ":");
            List<Vehiculo> disponibles = new ArrayList<>();

            for (int i = 0; i < vehiculosDisponibles.size(); i++){
                Vehiculo v = vehiculosDisponibles.get(i);
                float bateriaMinima = (usuario.getTipoUsuario() == Usuario.TipoUsuario.PREMIUM) ? 10.f : 20.0f;

                if (v.isDisponible() && v.getBateria() >= bateriaMinima && !v.isAveriado()){
                    System.out.println((disponibles.size()+1) + ". " + v.getNombre() + " (" + v.getTipo() + ") - Bateria: " + v.getBateria()+ "%");
                    disponibles.add(v);
                }
            }

            if (disponibles.isEmpty()){
                System.out.println("No hay vehiculos disponibles en esta base.");
                return;
            }

            System.out.println("\nSeleccione un vehiculo: ");
            int vehiculoSeleccionado = scanner.nextInt();
            scanner.nextLine();

            if (vehiculoSeleccionado <1 || vehiculoSeleccionado > disponibles.size()){
                System.out.println("\nSeleccion invalida.");
                return;
            }

            //Iniciamos el viaje con el vehiculo seleccionado y su base
            Vehiculo vehiculo = disponibles.get(vehiculoSeleccionado - 1);
            iniciarViaje(usuario, vehiculo, base.getNombre(), scanner);

        } else if (tipoVehiculo == 2){
          //Mostrar motos
          ArrayList<Vehiculo> motos = sistema.getMotos();
          List<Vehiculo> motosDisponibles = new ArrayList<>();

          System.out.println("\nMotos disponibles: ");
            System.out.println("--------------------");
          for (int i = 0; i < motos.size(); i++){
              Vehiculo v = motos.get(i);
              float bateriaMinima = (usuario.getTipoUsuario() == Usuario.TipoUsuario.PREMIUM) ? 10.0f : 20.0f;

              if (v.isDisponible() && v.getBateria() >= bateriaMinima && !v.isAveriado()){
                  if (v.getTipo() == Vehiculo.Tipo.MOTO) {
                      System.out.println((motosDisponibles.size() + 1) + ". " + v.getNombre() + " - Tipo de moto: " + v.getTipo() + "_PEQUENA - Bateria: " + v.getBateria() + "%");
                  } else {
                      System.out.println((motosDisponibles.size() + 1) + ". " + v.getNombre() + " - Tipo de moto: " + v.getTipo() + " - Bateria: " + v.getBateria() + "%");
                  }
                  motosDisponibles.add(v);
              }
          }

          if (motosDisponibles.isEmpty()){
              System.out.println("No hay motos disponibles");
              return;
          }

            System.out.println("--------------------\n");
          System.out.println("Seleccione una moto: ");
          int motoSeleccionada = scanner.nextInt();
          scanner.nextLine();

          if (motoSeleccionada < 1 || motoSeleccionada > motosDisponibles.size()){
              System.out.println("Opcion invalida");
              return;
          }

          //Iniciamos viaje con una moto y sus coordenadas iniciales ya predeterminadas
          Vehiculo moto = motosDisponibles.get(motoSeleccionada - 1);
          iniciarViaje(usuario, moto, "Ubicacion: [X:" + moto.getCooX() + ", Y:" + moto.getCooY() + "]", scanner);

        } else {
            System.out.println("Opcion invalida");
        }

    }

    //Finaliza el viaje actual
    private void finalizarViajeActual(Usuario usuario, SistemaVehiculos sistema, Scanner scanner){
        if (vehiculoActual.getTipo() == Vehiculo.Tipo.PATINETE || vehiculoActual.getTipo() == Vehiculo.Tipo.BICICLETA){
            //Verifica si las bases estan disponibles y las pone en una lista
            List<Base> basesDisponibles = new ArrayList<>();
            for (Base base : sistema.getBases()){
                if (!base.isAveriada()){
                    basesDisponibles.add(base);
                }
            }

            if (basesDisponibles.isEmpty()){
                System.out.println("No hay bases disponibles para devolver el vehiculo");
                return;
            }


            System.out.println("Seleccione una base para devolver el vehiculo: ");

            //Muestra las bases disponibles
            for (int i = 0; i < basesDisponibles.size(); i++){
                Base base = basesDisponibles.get(i);
                if (base.tieneEspacio()){
                    System.out.println((i+1) + ". " + base.getNombre() + " (con espacio disponible)");
                } else {
                    System.out.println((i+1) + ". " + base.getNombre() + " (base llena)");
                }
            }

            System.out.println("\nSeleccione una base:");
            int baseSeleccionada = scanner.nextInt();
            scanner.nextLine();

            if (baseSeleccionada < 1 || baseSeleccionada > basesDisponibles.size()){
                System.out.println("\nOpcion invalida");
                return;
            }

            Base baseDestino = basesDisponibles.get(baseSeleccionada - 1);
            if (!baseDestino.tieneEspacio()){
                System.out.println("\nEsta base esta llena. Seleccione otra base.");
                return;
            }

            //Quitar el vehiculo de la base
            sistema.removerVehiculoDeBase(vehiculoActual);

            //Agregar el vehiculo a la base
            baseDestino.agregarVehiculo(vehiculoActual);

            //Llama a finalizar viaje, pasandole todos los atributos necesarios
            finalizarViaje(usuario, baseDestino.getNombre(), sistema, scanner);

        } else if (vehiculoActual.getTipo() == Vehiculo.Tipo.MOTO || vehiculoActual.getTipo() == Vehiculo.Tipo.MOTO_GRANDE){
            //Si es una moto, pide las coordenadas de donde se deja
            System.out.println("\nLIMIITES DE LA CIUDAD:");
            System.out.println(sistema.getCiudad());

            System.out.println("\nIndique las coordenadas y ubicacion de donde deja la moto:");
            System.out.println("Coordenadas X: ");
            int coordX = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Coordenadas Y: ");
            int coordY = scanner.nextInt();
            scanner.nextLine();

            if (!sistema.getCiudad().coordenadasValidas(coordX, coordY)){
                System.out.println("\nERROR: COORDENADAS FUERA DE LIMITES DE LA CIUDAD. NO SE PUEDE FINALIZAR VIAJE");
                return;
            }

            String ubicacionFin = String.format("[X:%d, Y:%d]", coordX, coordY);
            finalizarViaje(usuario, ubicacionFin, sistema, scanner);

        }
    }

}
