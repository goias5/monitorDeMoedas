import java.text.DecimalFormat;

public class Cotacao {
    private String cotacaoCompra;
    private String cotacaoVenda;
    private String dataHoraCotacao;

    public Cotacao(String cotacaoCompra, String cotacaoVenda, String dataHoraCotacao) {
        this.cotacaoCompra = cotacaoCompra;
        this.cotacaoVenda = cotacaoVenda;
        this.dataHoraCotacao = dataHoraCotacao;
    }

    public Cotacao() {
    }

    public String getCotacaoCompra() {
        return cotacaoCompra;
    }

    public void setCotacaoCompra(String cotacaoCompra) {
        this.cotacaoCompra = cotacaoCompra;
    }

    public String getCotacaoVenda() {
        return cotacaoVenda;
    }

    public void setCotacaoVenda(String cotacaoVenda) {
        this.cotacaoVenda = cotacaoVenda;
    }

    public String getDataHoraCotacao() {
        return dataHoraCotacao;
    }

    public void setDataHoraCotacao(String dataHoraCotacao) {
        this.dataHoraCotacao = dataHoraCotacao;
    }

    @Override
    public String toString() {
        return "Cotacao{" +
                "cotacaoCompra='" + cotacaoCompra + '\'' +
                ", cotacaoVenda='" + cotacaoVenda + '\'' +
                ", dataHoraCotacao='" + dataHoraCotacao + '\'' +
                '}';
    }

    public String calculaVariacao(String anterior){
        DecimalFormat df2 = new DecimalFormat("#0.00");
        double cotacaoAtual = Double.parseDouble(this.cotacaoCompra);
        double cotacaoAnterior = Double.parseDouble(anterior);
        double diferenca = cotacaoAtual-cotacaoAnterior;
        double porcentagemDiferenca = 100*(diferenca/cotacaoAtual);
        String diferencaFormatada = df2.format(porcentagemDiferenca);
        String cotacao;
        if (cotacaoAtual>cotacaoAnterior){
            cotacao = "+".concat(diferencaFormatada);
        }
        else{
            cotacao = diferencaFormatada;
        }
        return cotacao;
    }

    public String calculaDiferenca(String anterior){
        DecimalFormat df2 = new DecimalFormat("#0.00");
        double cotacaoAtual = Double.parseDouble(this.cotacaoCompra);
        double cotacaoAnterior = Double.parseDouble(anterior);
        double diferenca = cotacaoAtual-cotacaoAnterior;
        String diferencaFormatada = df2.format(diferenca);
        String cotacao;
        if (cotacaoAtual>cotacaoAnterior){
            cotacao = "+".concat(diferencaFormatada);
        }
        else{
            cotacao = diferencaFormatada;
        }
        return cotacao;
    }
}
