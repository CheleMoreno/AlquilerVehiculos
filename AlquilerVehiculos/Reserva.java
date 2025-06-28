import java.time.LocalDateTime;

public class Reserva {
    private Vehiculo vehiculo;
    private LocalDateTime fechaReserva;
    private LocalDateTime expiracion;

    //Constructor para distintas reservas de vehiculos
    public Reserva(Vehiculo vehiculo, LocalDateTime fechaReserva){
        this.vehiculo = vehiculo;
        this.fechaReserva = fechaReserva;
        this.expiracion = fechaReserva.plusMinutes(20);
    }

    //Getters
    public Vehiculo getVehiculo(){
        return vehiculo;
    }

    public LocalDateTime getExpiracion(){
        return expiracion;
    }

    //Revisa si pasaron 20 mins desde la reserva
    public boolean estaActiva(){
        return LocalDateTime.now().isBefore(expiracion);
    }
}
