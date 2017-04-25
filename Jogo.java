package com.itc.jogo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Jogo {

	private static String[][] array = new String[5][5];
	private static int[] localTa = { 0, 0 };
	private static int[] localFormiga = { 4, 4 };
	private static boolean positivoT = true;
	private static boolean positivoF = false;

	public static String BAIXO = "B";

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			for (int y = 0; y < 5; y++) {
				array[i][y] = " ";
			}
		}

		FileReader arq;
		String mov = "";
		try {
			arq = new FileReader("C:/temp/movimentacao.txt");
			BufferedReader lerArq = new BufferedReader(arq);
			mov = lerArq.readLine();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] movimentacaoSplit = mov.split(",");

		String tamandua = "T";
		String comida = "C";

		String formiga = "F";
		array[0][0] = tamandua;
		array[4][4] = formiga;
		array[1][3] = comida;
		array[2][1] = comida;
		array[4][3] = comida;
		int m = 0;
		printGame();
		do {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			movimentoFormiga(movimentacaoSplit[m]);
			if (TamanduaPegouFormiga()) {
				break;
			}
			movimentoTamandua();
			if (TamanduaPegouFormiga()) {
				break;
			}
			printGame();
			m++;
		} while (m < movimentacaoSplit.length);
		printGame();
		if (TamanduaPegouFormiga()) {
			System.out.println("TAMANDUÁ COMEU O FORMIGA!");
		} else {
			System.out.println("FORMIGA ESCAPOU!");
		}
	}

	public static void movimentoTamandua() {

		Random gerador = new Random();
		if (gerador.nextInt(10) < 5) {
			array[localTa[0]][localTa[1]] = " ";
			if (positivoT) {
				try {
					localTa[0] = localTa[0] + 1;
					array[localTa[0]][localTa[1]] = "T";
				} catch (ArrayIndexOutOfBoundsException e) {
					localTa[0] = localTa[0] - 2;
					array[localTa[0]][localTa[1]] = "T";
					positivoT = false;
				}
			} else {
				try {
					localTa[0] = localTa[0] - 1;
					array[localTa[0]][localTa[1]] = "T";
				} catch (ArrayIndexOutOfBoundsException e) {
					localTa[0] = localTa[0] + 2;
					array[localTa[0]][localTa[1]] = "T";
					positivoT = true;
				}
			}
		} else {
			array[localTa[0]][localTa[1]] = " ";
			if (positivoT) {
				try {
					localTa[1] = localTa[1] + 1;
					array[localTa[0]][localTa[1]] = "T";
				} catch (ArrayIndexOutOfBoundsException e) {
					localTa[1] = localTa[1] - 2;
					array[localTa[0]][localTa[1]] = "T";
					positivoT = false;
				}
			} else {
				try {
					localTa[1] = localTa[1] - 1;
					array[localTa[0]][localTa[1]] = "T";
				} catch (ArrayIndexOutOfBoundsException e) {
					localTa[1] = localTa[1] + 2;
					array[localTa[0]][localTa[1]] = "T";
					positivoT = true;
				}
			}
		}

	}

	public static void movimentoFormiga(String movimentacao) {
		int i = localFormiga[0];
		int x = localFormiga[1];
		try {
			
			array[localFormiga[0]][localFormiga[1]] = " ";
			if ("B".equals(movimentacao)) {

				localFormiga[0] = localFormiga[0] + 1;
				if (!localLivre(localFormiga)) {
					localFormiga[0] = localFormiga[0] - 1;
				}
				array[localFormiga[0]][localFormiga[1]] = "F";

			} else if ("C".equals(movimentacao)) {

				localFormiga[0] = localFormiga[0] - 1;
				if (!localLivre(localFormiga)) {
					localFormiga[0] = localFormiga[0] + 1;
				}
				array[localFormiga[0]][localFormiga[1]] = "F";

			} else if ("D".equals(movimentacao)) {

				localFormiga[1] = localFormiga[1] + 1;
				if (!localLivre(localFormiga)) {
					localFormiga[1] = localFormiga[1] - 1;
				}
				array[localFormiga[0]][localFormiga[1]] = "F";

			} else if ("E".equals(movimentacao)) {

				localFormiga[1] = localFormiga[1] - 1;
				if (!localLivre(localFormiga)) {
					localFormiga[1] = localFormiga[1] + 1;
				}
				array[localFormiga[0]][localFormiga[1]] = "F";

			}
		} catch (ArrayIndexOutOfBoundsException e) {
			localFormiga[0] = i;
			localFormiga[1] = x;
			array[i][x] = "F";
		}
	}

	public static boolean localLivre(int[] local) throws ArrayIndexOutOfBoundsException {		
		return !(array[local[0]][local[1]]).equals("C");
	}

	public static void printGame() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
		System.out.println("+-------------------+");
		for (int i = 0; i < 5; i++) {
			System.out.println("| " + array[i][0] + " | " + array[i][1] + " | " + array[i][2] + " | " + array[i][3]
					+ " | " + array[i][4] + " | ");
			System.out.println("+-------------------+");
		}
	}

	public static boolean TamanduaPegouFormiga() {
		if (localTa[0] == localFormiga[0] && localTa[1] == localFormiga[1]) {
			array[localFormiga[0]][localFormiga[1]] = "T";
			return true;
		}
		return false;
	}

}
