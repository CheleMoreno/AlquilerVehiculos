public class Tarifas {
    //Se declaran las tarifas
    public static double biciStandard = 0.15;
    public static double biciPremium = 0.13;

    public static double patinStandard = 0.20;
    public static double patinPremium = 0.18;

    public static double motoStandard = 0.30;
    public static double motoPremium = 0.27;

    public static double motoGrandeStandard = 0.35;
    public static double motoGrandePremium = 0.31;

    //Se muestran las distintas tarifas
    public static void mostrarTarifas(){
        System.out.println("\n--- TARIFAS ACTUALES ---");
        System.out.printf("BICICLETA:   Standard: %.2f €/min | Premium: %.2f €/min", biciStandard, biciPremium);
        System.out.printf("\nPATINETE:    Standard: %.2f €/min | Premium: %.2f €/min", patinStandard, patinPremium);
        System.out.printf("\nMOTO:        Standard: %.2f €/min | Premium: %.2f €/min", motoStandard, motoPremium);
        System.out.printf("\nMOTO GRANDE: Standard: %.2f €/min | Premium: %.2f €/min", motoGrandeStandard, motoGrandePremium);
    }
}
