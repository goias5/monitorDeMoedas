import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Controller {
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label cotacaoVenda;
    @FXML
    private Label cotacaoCompra;
    @FXML
    private Button botaoPesquisar;
    @FXML
    private Label dataEHora;
    @FXML
    private Label variacao7dias;
    @FXML
    private Label variacao30dias;
    @FXML
    private Label variacao180dias;
    @FXML
    private Label valor7dias;
    @FXML
    private Label valor30dias;
    @FXML
    private Label valor180dias;


    public void initialize(){
    }

    @FXML
    public void handlePesquisa() throws IOException {
        LocalDate date = datePicker.getValue();
        Gson gson = new Gson();
        JsonDataFile download = new JsonDataFile(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        Cotacao cotacao = gson.fromJson(download.getConteudoArquivoFormatado(), Cotacao.class);

        cotacaoVenda.setText("R$"+cotacao.getCotacaoVenda());
        cotacaoCompra.setText("R$"+cotacao.getCotacaoCompra());
        dataEHora.setText(cotacao.getDataHoraCotacao());


        LocalDate date2 = date.minusDays(7);
        JsonDataFile download2 = new JsonDataFile(date2.getDayOfMonth(), date2.getMonthValue(), date2.getYear());
        Cotacao cotacao7dias = gson.fromJson(download2.getConteudoArquivoFormatado(), Cotacao.class);
        String variacao1 = cotacao.calculaVariacao(cotacao7dias.getCotacaoCompra());


        LocalDate date3 = date.minusDays(30);
        JsonDataFile download3 = new JsonDataFile(date3.getDayOfMonth(), date3.getMonthValue(), date3.getYear());
        Cotacao cotacao30dias = gson.fromJson(download3.getConteudoArquivoFormatado(), Cotacao.class);
        String variacao2 = cotacao.calculaVariacao(cotacao30dias.getCotacaoCompra());

        LocalDate date4 = date.minusDays(180);
        JsonDataFile download4 = new JsonDataFile(date4.getDayOfMonth(), date4.getMonthValue(), date4.getYear());
        Cotacao cotacao180dias = gson.fromJson(download4.getConteudoArquivoFormatado(), Cotacao.class);
        String variacao3 = cotacao.calculaVariacao(cotacao180dias.getCotacaoCompra());

        variacao7dias.setText(variacao1+"%");
        variacao30dias.setText(variacao2+"%");
        variacao180dias.setText(variacao3+"%");

        valor7dias.setText(cotacao.calculaDiferenca(cotacao7dias.getCotacaoCompra()));
        valor30dias.setText(cotacao.calculaDiferenca(cotacao30dias.getCotacaoCompra()));
        valor180dias.setText(cotacao.calculaDiferenca(cotacao180dias.getCotacaoCompra()));

        Main.deleteTree(new File(Main.pegaCaminhoBase()));
    }
}
