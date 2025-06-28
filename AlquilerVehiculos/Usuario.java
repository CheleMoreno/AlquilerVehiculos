import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario extends Persona{

    public enum TipoUsuario {STANDARD, PREMIUM};
    protected TipoUsuario tipoUsuario;
    protected Double saldo;
    protected Viajes viaje;
    protected boolean activo = true;
    //Para visualizar en BlueJ
    private Viajes dummyViaje;

    Usuario(String nombre, String apellido, String email, int dni, int telefono, Double saldo) {
        super(nombre, apellido, email, dni, telefono);
        this.saldo = saldo;
        this.tipoUsuario = TipoUsuario.STANDARD;
        this.viaje = new Viajes();
    }

    //Getters y setters
    public boolean isActivo(){ return activo; }
    public void setActivo(boolean activo){ this.activo = activo;}
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    public Double getSaldo(){
        return saldo;
    }
    public void reducirSaldo(double cantidad) {this.saldo = this.saldo - cantidad;}

    //Hacemos un loop para el menu
    public void mostrarMenu(Scanner scanner, SistemaVehiculos sistemaVehiculos){
        boolean menuUsuario = true;
        while (menuUsuario) {
            System.out.println("\n--- Bienvenid@ " + this.getNombre() + "! ---");
            System.out.println("1. Ver todos los vehiculos disponibles");
            System.out.println("2. Alquilar un vehiculo");
            System.out.println("3. Informar un problema");
            System.out.println("4. Historial de viajes");
            System.out.println("5. Saldo");
            //Opciones unicas para usuarios PREMIUM
            if (this.getTipoUsuario() == TipoUsuario.PREMIUM){
                System.out.println("6. Reserva con anticipacion");
                System.out.println("7. Iniciar viaje con vehiculo reservado");
            }
            System.out.println("0. CERRAR SESION \n-----------------------");
            System.out.println("Selecciona una opcion:");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion){
                case 1:
                    //Llamamos a metodos para mostrar las listas de todos los vehiculos
                    sistemaVehiculos.mostrarMotosDisponibles();
                    sistemaVehiculos.mostrarBasesConVehiculos();
                    System.out.println("\nPresione Enter para regresar");
                    scanner.nextLine();
                    break;

                case 2:
                    //Abrimos el menu de viajes del usuario
                    viaje.menuViajes(this, sistemaVehiculos, scanner);
                    break;

                case 3:
                    //Abrimos el menu de reportar problemas del usuario
                    reportarProblema(scanner, sistemaVehiculos);
                    break;

                case 4:
                    //Mostramos el historial de viajes del usuario
                    viaje.mostrarHistorial();
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 5:
                    //Pantalla de saldo, donde se muestra y se puede agregar
                    System.out.println("\n--- VERIFICACION Y RECARGA SALDO ---");
                    System.out.println("-----------------------------------");
                    System.out.println("Su saldo actual es: " + String.format("%.2f€", this.saldo));
                    System.out.println("\n1. Agregar saldo");
                    System.out.println("2. Volver");
                    System.out.println("-------------");
                    System.out.println("Elige una opcion: ");
                    int op5 = scanner.nextInt();
                    scanner.nextLine();
                    switch (op5){
                        case 2:
                            break;

                        case 1:
                            System.out.println("\nIntroduzca el monto a agregar: ");
                            double saldoAgregado = scanner.nextDouble();
                            scanner.nextLine();

                            this.saldo = this.saldo + saldoAgregado;
                            System.out.println("\n--- Saldo agregado con exito! ---");
                            System.out.println("Nuevo saldo: " + String.format("%.2f€", this.saldo));
                            System.out.println("\nPresione Enter para continuar...");
                            scanner.nextLine();
                            break;
                    }
                    break;

                case 6:
                    //Verificacion si es PREMIUM primero, luego reserva un vehiculo poe 20 mins
                    if (this.getTipoUsuario() == TipoUsuario.PREMIUM){
                        ((UsuarioPremium)this).reservarVehiculo(scanner, sistemaVehiculos);
                    } else {
                        System.out.println("Esta opcion es solo para usuarios PREMIUM");
                    }
                    break;

                case 7:
                    //Verifica si es PREMIUM y muestra los vehiculos reservados y puede iniciar viaje con ellos
                    if (this.getTipoUsuario() == TipoUsuario.PREMIUM){
                        ((UsuarioPremium)this).iniciarViajeConReserva(scanner, sistemaVehiculos);
                    } else {
                        System.out.println("Esta opcion es solo para usuarios PREMIUM");
                    }
                    break;

                    //Sale del loop y cierra sesion
                case 0:
                    menuUsuario = false;
                    System.out.println("Cerrando sesion... \n\n");
                    break;

                    //Vuelve al loop
                default:
                    System.out.println("Opcion invalida. Intente de nuevo");
                    break;
            }

        }
    }

    //Menu para reportar un vehiculo o una base averiada
    public void reportarProblema(Scanner scanner, SistemaVehiculos sistema){
        System.out.println("\nQue desea reportar?");
        System.out.println("1. Vehiculo averiado");
        System.out.println("2. Base averiada");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        //Se hace una lista de vehiculos donde se agregan solo si estan averiados. Esta lista recorre todas las motos y vehiculos en bases.
        //Luego se muestran solo los vehiculos averiados
        switch (opcion){
            case 1:
                System.out.println("\nVehiculos disponibles a reportar: ");
                int i = 1;
                List<Vehiculo> todosVehiculos = new ArrayList<>();

                for (Vehiculo v : sistema.getMotos()){
                    if (!v.isAveriado()) {
                        System.out.println(i + ". " + v.getNombre() + " (" + v.getTipo() +")");
                        todosVehiculos.add(v);
                        i++;
                    }
                }

                for (Base base : sistema.getBases()){
                    for (Vehiculo v : base.getVehiculos()){
                        if (!v.isAveriado() && !base.isAveriada()){
                            System.out.println(i + ". " + v.getNombre() + " (" + v.getTipo() + ") en la base" + base.getNombre() );
                            todosVehiculos.add(v);
                            i++;
                        }
                    }

                }

                //Verifica si la lista tiene elementos para elegir, si no, regresa
                if (todosVehiculos.isEmpty()){
                    System.out.println("\nNo hay vehiculos disponibles para reportar.");
                    return;
                }

                System.out.println("\nSeleccione el vehiculo:");
                int seleccion = scanner.nextInt();
                scanner.nextLine();

                //Apuntamos al vehiculo elegido y lo ponemos como averiado
                if (seleccion >= 1 && seleccion <= todosVehiculos.size()){
                    todosVehiculos.get(seleccion - 1).setAveriado(true);
                    System.out.println("-------------------------------\nVehiculo reportado como averiado!");
                    System.out.println("\nPresiona Enter para continuar");
                    scanner.nextLine();
                } else {
                    System.out.println("Seleccion invalida.");
                }
                break;

            case 2:
                //Creamos dos listas, una con todas las bases y otra con las bases averiadas, disponibles para reportar
                List<Base> bases = sistema.getBases();
                List<Base> basesDisponibles = new ArrayList<>();

                for (Base b : bases){
                    if (!b.isAveriada()){
                        basesDisponibles.add(b);
                    }
                }

                //Verificamos si esta vacia
                if (basesDisponibles.isEmpty()){
                    System.out.println("\nNo hay bases disponibles para reportar.");
                    return;
                }

                //Mostramos las bases
                System.out.println("\nBases disponibles a reportar: ");
                for (int j = 0; j < basesDisponibles.size(); j++){
                    System.out.println(j + 1 + ". " + bases.get(j).getNombre());
                }

                System.out.println("\nSelecciona la base:");
                int baseSeleccionada = scanner.nextInt();
                scanner.nextLine();

                //Apuntamos a la base y se cambia el valor a averiada
                if (baseSeleccionada >= 1 && baseSeleccionada <= basesDisponibles.size()){
                    Base base = basesDisponibles.get(baseSeleccionada - 1);
                    base.setAveriada(true);
                    System.out.println("\nBase reportada como averiada!");
                    System.out.println("-------------------------------\nPresione Enter para continuar");
                    scanner.nextLine();
                } else {
                    System.out.println("Seleccion invalida");
                } break;

            default:
                System.out.println("Opcion invalida");

        }
    }

}

