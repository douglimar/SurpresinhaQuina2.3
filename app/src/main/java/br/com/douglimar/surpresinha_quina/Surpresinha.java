package br.com.douglimar.surpresinha_quina;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Douglimar Moraes on 02/12/17.
 *
 *
 */

class Surpresinha {

    public String generateQuinaGame() {

        /* Regra do Jogo:
	    * O apostador pode escolher 5 numeros entre 50 numeros disponiveis
	    */

        int numerosQuina[] = new int[5] ;

        int indice;
        Random random = new Random();
        StringBuilder Retorno = new StringBuilder();

        for (int i = 0 ; i < 5; i++) {

            // Gera um numero aleatorio menor ou igual a 80
            indice = random.nextInt(81);

            // Consiste o nro. gerado, para garantir que ele é unico
            for (int k = 0; k <= 80; k++) {
                if (consisteJogo(numerosQuina, indice) || indice == 0 ) {
                    indice = random.nextInt(81);
                }
            }

            // Adiciona o numero gerado dentro de um Array
            numerosQuina[i] = indice;
        }

        // Ordena o Array de numeros gerados
        Arrays.sort(numerosQuina);

        for (int i = 0; i < 5; i++) {

            if (numerosQuina[i] < 10 )
                Retorno.append(" 0").append(numerosQuina[i]);
            else
                Retorno.append(" ").append(numerosQuina[i]);
        }

        return Retorno.toString();

    }

    private boolean consisteJogo(int pArray[], int PNumero) {

        boolean Retorno = false;

        for (int aPArray : pArray) {
            if (aPArray == PNumero) {
                Retorno = true;
                break;
            }
        }

        return Retorno;
    }

}
