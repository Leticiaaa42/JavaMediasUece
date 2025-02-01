import java.io.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Scanner teclado_string = new Scanner(System.in);
		Scanner teclado_int = new Scanner(System.in);
		int menu;
		while (true){
			System.out.println("Selecione uma opcao digitando um numero");
			System.out.println("1 - inserir respostas da turma de uma disciplina");
			System.out.println("2 - inserir gabarito oficial de uma disciplina");
			System.out.println("3 - gerar resultado de disciplina");
			System.out.println("4 - sair do programa");
			menu = teclado_int.nextInt();
			
			if (menu == 1){
				System.out.println("Insira o nome da disciplina: ");
				String nome_disciplina = teclado_string.nextLine();
				try {
					FileWriter fw = new FileWriter(nome_disciplina + "_provas.txt", true);
					BufferedWriter bw = new BufferedWriter(fw);
					
					System.out.println("Insira o numero de alunos: ");
					int num_alunos = teclado_int.nextInt();
					
					for (int i = 0; i < num_alunos; i++){
						System.out.println("Insira o nome do aluno numero " + (i+1) + ":");
						String nome_aluno = teclado_string.nextLine();
						
						boolean loop1 = true;
						String resposta_aluno = null;
						while (loop1){
							System.out.println("Insira as respostas (ex: VFFFVVVFVF): ");
							resposta_aluno = teclado_string.nextLine();
							if (resposta_aluno.length() == 10){
								loop1 = false;
							}
						}
						
						bw.write(resposta_aluno + "\t" + nome_aluno + "\n");
						
					}
					
					bw.close();
					fw.close();
					System.out.println("Respostas da turma registradas com sucesso!");
				}
				catch (IOException e){
					System.out.println("-= Erro de IO =-");
				}
			}
			else if (menu == 2){
				System.out.println("Insira o nome da disciplina: ");
				String nome_disciplina = teclado_string.nextLine();
				try {
					FileWriter fw = new FileWriter(nome_disciplina + "_gabarito-oficial.txt", false);
					BufferedWriter bw = new BufferedWriter(fw);
					
					boolean loop2 = true;
					String gabarito = null;
					while (loop2){
						System.out.println("Insira as respostas (ex: VFFFVVVFVF): ");
						gabarito = teclado_string.nextLine();
						if (gabarito.length() == 10){
							loop2 = false;
						}
					}
					
					bw.write(gabarito);
					
					bw.close();
					fw.close();
					System.out.println("Respostas da turma registradas com sucesso!");
				}
				catch (IOException e){
					System.out.println("-= Erro de IO =-");
				}
			}
			else if (menu == 3){
				//AQUI A SUA PARTE
			}
			else if (menu == 4){
				break;
			}
		}
	}
}