public class Ciudad {
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    //Constructor para crear diferentes ciudades con distintos limites
    public Ciudad (int xMin, int xMax, int yMin, int yMax){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    //Verifica si las coordenadas que recibe estan dentro de los limites del Objeto de Ciudad creado
    public boolean coordenadasValidas(double x, double y){
        return (x >= xMin && x <=xMax && y >= yMin && y <= yMax );
    }

    //Muestra en pantalla con un get
    @Override
    public String toString(){
        return String.format("Limites de la ciudad: X[%.0f, %.0f], Y[%.0f, %.0f]", xMin, xMax, yMin, yMax);
    }
}
