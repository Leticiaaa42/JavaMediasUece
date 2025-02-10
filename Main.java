import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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

      File provas = new File("provas");
      File gabaritos = new File("gabaritos");
      File resultados = new File("resultados");
      provas.mkdir();
      gabaritos.mkdir();
      resultados.mkdir();
			
			if (menu == 1){
				System.out.println("Insira o nome da disciplina: ");
				String nome_disciplina = teclado_string.nextLine();
				try {
          File provaNome = new File(provas, nome_disciplina + ".txt");
					FileWriter fw = new FileWriter(provaNome, true);
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
          System.err.println(e);
				}
			}
			else if (menu == 2){
				System.out.println("Insira o nome da disciplina: ");
				String nome_disciplina = teclado_string.nextLine();
				try {
          File gabaritoNome = new File(gabaritos, nome_disciplina + ".txt");
					FileWriter fw = new FileWriter(gabaritoNome, false);
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
          System.err.println(e);
				}
			}
			else if (menu == 3){
        System.out.println("Insira o nome da disciplina");
        String nome_disciplina = teclado_string.nextLine();
        try {
          //Carregar arquivo
          File gabaritoFile = new File(gabaritos, nome_disciplina + ".txt");
          File provaFile = new File(provas, nome_disciplina + ".txt");

          boolean userDefined = true;
          String input = "";
          FileReader leitorGabarito = null;

          try {
            leitorGabarito = new FileReader(gabaritoFile);
            System.out.println("Gabarito encontrado na pasta gabaritos, deseja usar esse arquivo? (s/n)");
            input = teclado_string.nextLine();
            if (input.equals("s"))
              userDefined = false;
          } catch (IOException e) {
            userDefined = true;
          } finally {
            while (userDefined) {
              System.out.println("Digite o caminho do arquivo gabarito");
              input = teclado_string.nextLine();

              try {
                leitorGabarito = new FileReader(input);
                userDefined = false;
              } catch (IOException e) {
                System.out.println("Caminho inválido");
              }
            }
          }

          FileReader leitorProva = new FileReader(provaFile);

          BufferedReader bwGab = new BufferedReader(leitorGabarito);
          BufferedReader bwPro = new BufferedReader(leitorProva);

          //Calcular notas
          String gabarito = bwGab.readLine();
          String provaLine = bwPro.readLine();
          Map<String, Integer> alunos = new HashMap<String, Integer>();
          double media = 0;
          while (provaLine != null) {
            String[] prova = provaLine.split("\t");

            int p = 0;
            boolean zero = false;
            if (prova[0].equals("VVVVVVVVVV") || prova[0].equals("FFFFFFFFFF"))
              zero = true;

            for (int i = 0; i < 10 && !zero; i++) {
              if (gabarito.charAt(i) == prova[0].charAt(i))
                p++;
            }

            alunos.put(prova[1], p);
            provaLine = bwPro.readLine();
            media += p;
          }
          media /= alunos.size();

          //Organizar notas
          Map<String, Integer> porNota = sortByValue(alunos);
          Map<String, Integer> porNome = sortByKey(alunos);

          //Escrever resultados
          File resultadoPorNota = new File(resultados, nome_disciplina + "_por_nota.txt");
          File resultadoPorNome = new File(resultados, nome_disciplina + "_por_nome.txt");

          FileWriter fwNota = new FileWriter(resultadoPorNota);
          FileWriter fwNome = new FileWriter(resultadoPorNome);

          BufferedWriter bwNota = new BufferedWriter(fwNota);
          BufferedWriter bwNome = new BufferedWriter(fwNome);
          
          for (Map.Entry<String, Integer> entry : porNota.entrySet()) {
            bwNota.write(entry.getKey() + "\t" + entry.getValue() + "\n");
          }
          bwNota.write("Média da turma:\t" + media + "\n");

          for (Map.Entry<String, Integer> entry : porNome.entrySet()) {
            bwNome.write(entry.getKey() + "\t" + entry.getValue() + "\n");
          }

          //Fechar Buffers
          bwNota.close();
          bwNome.close();
          bwGab.close();
          bwPro.close();
        } catch (IOException e) {
					System.out.println("-= Erro de IO =-");
          System.err.println(e);
        }
			}
			else if (menu == 4){
				break;
			}
		}

    teclado_int.close();
    teclado_string.close();
	}

  private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Entry.comparingByValue());
    list = reverseList(list);

    Map<K, V> result = new LinkedHashMap<>();
    for (Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

  private static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
    List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Entry.comparingByKey());

    Map<K, V> result = new LinkedHashMap<>();
    for (Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

  private static <T> List<T> reverseList(List<T> list) {
    List<T> reversed = new ArrayList<T>();

    int size = list.size();
    for (int i = size - 1; i >= 0; i--) {
      reversed.add(list.get(i));
    }

    return reversed;
  }
}
