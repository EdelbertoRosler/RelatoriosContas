package relatorios;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RelatoriosContas {
    public static void main(String[] args) {

//************************* Menu principal ****************************\\
        boolean validacao = true;
        while (validacao){
            System.out.println("______________________________");
            System.out.println("1 - Relatório de todas contas");
            System.out.println("2 - Transações de uma conta");
            System.out.println("0 - Sair");
            System.out.println("______________________________");
            System.out.println("Escolha a opção desejada: ");

//*********************************************************************\\
            TreeMap<Integer, ArrayList<Transacao>> mapContas = new TreeMap<>();
            try {

                Scanner scan = new Scanner(System.in);
                int E = scan.nextInt();

                JSONArray transacoes = leArquivoJSON("src/relatorios/transactions.json");

//*************** Percorrendo a estrutura(s) de dados *****************\\
                for (int i = 0; i < transacoes.length(); i++) {
                    JSONObject obj = transacoes.getJSONObject(i);
                    int idConta = obj.getInt("id_conta");
                    Transacao t = new Transacao(obj.getInt("id_transacao"), obj.getInt("id_conta"), obj.getDouble("valor"));
                    if (!mapContas.containsKey(idConta)) {
                        ArrayList<Transacao> trans = new ArrayList<>();
                        trans.add(t);
                        mapContas.put(idConta, trans);
                    } else {
                        mapContas.get(idConta).add(t);
                    }
                }
//****************** Relatório de todas as contas *********************\\
                if (E == 1) {
                    System.out.println("Conta     " + "N° Trans.          " + "Saldo");
                    Set<Integer> contas = mapContas.keySet();
                    for (Integer id : contas) {
                        ArrayList<Transacao> array2 = mapContas.get(id);
                        double saldo = 0.0;
                        for (Transacao j : array2) {
                            saldo += j.getValor();
                        }
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);

                        System.out.print(id + "      ");
                        System.out.print(array2.size() + "                  ");
                        System.out.println("R$ " + df.format(saldo));
                    }
                }

//***************** Relatório de uma conta Específica *******************\\
                else if (E == 2) {
                    Scanner scans = new Scanner(System.in);
                    System.out.println("N° da conta: ");
                    int Nc = scans.nextInt();//recebe o número da conta
                    ArrayList<Transacao> array1 = mapContas.get(Nc);
                    System.out.println("Id        " + "Valor");
                    double saldo = 0.0;
                    for (Transacao t : array1) {
                        System.out.println(t.getIdTran() + "        R$ " + t.getValor());
                        saldo += t.getValor();
                    }
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);

                    System.out.println("--> Saldo: " + df.format(saldo));
                }
                else if (E == 0 || E > 2) {
                    System.out.println("Bye Bye!!");
                    break;
                }
                else validacao = false;//fim do loop
            }

//***************************** Exceptions *******************************\\
            catch (FileNotFoundException ex) {
                System.out.println("Arquivo não encontrado!");
            }
            catch (JSONException ex) {
                System.out.println("Erro na manipulação dos objetos JSON!");
            }
            catch (InputMismatchException scans) {
                System.out.println("Bye Bye!!");
                break;
            }
        }
    }

    public static JSONArray leArquivoJSON(String nomeArquivo) throws FileNotFoundException, JSONException {
        Scanner arquivo = new Scanner(new File(nomeArquivo));
        String jsonStr = "";
        while(arquivo.hasNext()) {
            jsonStr += arquivo.nextLine();
        }
        return new JSONArray(jsonStr);
    }


}