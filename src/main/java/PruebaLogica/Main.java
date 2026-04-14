package PruebaLogica;


import java.util.Arrays;

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

    public static int productoMaximoDe3(int[] arreglo){
        Arrays.sort(arreglo);
        int longitud = arreglo.length;
        int opcion1 = arreglo[longitud -1] * arreglo[longitud -2] * arreglo[longitud-3];
        int opcion2 = arreglo[0] * arreglo[1] * arreglo[longitud-1];
        return Math.max(opcion1,opcion2);
    }
    public static void main(String[] args) {
        int[] arreglo1 = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(kadane(arreglo1));
        int[] arreglo2 = {-10, -10, 5, 2};
        System.out.println(productoMaximoDe3(arreglo2));
    }
}
