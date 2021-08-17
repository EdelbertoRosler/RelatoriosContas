package relatorios;

import java.text.DecimalFormat;

public class Transacao {
    private int idTran;
    private int idConta;
    private double valor;

    public Transacao(int idTran, int idConta, double valor) {
        this.idTran = idTran;
        this.idConta = idConta;
        this.valor = valor;
    }
    
    public int getIdTran() {
        return idTran;
    }
    
    public int getIdConta() {
        return idConta;
    }

    public double getValor() {
        return valor;
    }
    
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        String returnable = this.idTran + "\t";
        returnable += "R$ " + df.format(this.valor);
        return returnable;
    }
}
