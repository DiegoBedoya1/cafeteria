package PruebaLogica;


public class Main {

    public static int kadane(int[] arreglo){
        int maximoGlobal = arreglo[0];
        int maximoActual = arreglo[0];
        for(int i = 1; i<arreglo.length; ++i){
             maximoActual = Math.max(arreglo[i],arreglo[i]+ maximoActual);
             if (maximoActual > maximoGlobal){
                 maximoGlobal = maximoActual;
             }
        }
        return maximoGlobal;
    }
    public static void main(String[] args) {
        int[] arreglo = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(kadane(arreglo));
    }
}
