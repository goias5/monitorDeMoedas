import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class JsonDataFile {
    private String conteudoArquivo;
    private String conteudoArquivoFormatado;
    private Scanner scanner;
    private String url;
    private String localDeSalvamentoDoArquivo;
    private URL linkArquivo;
    private File file;
    private Path filePath;
    private URLConnection urlConnection;
    private InputStream fileInputStream;

    public String getConteudoArquivoFormatado() {
        return conteudoArquivoFormatado;
    }

    public JsonDataFile(int dia, int mes, int ano) throws IOException {
        this.scanner = new Scanner(System.in);
        this.url = selecionarURLDeDownload(dia, mes, ano);
        this.localDeSalvamentoDoArquivo = selecionarLocalDeSalvamentoDoArquivo(dia,mes,ano);
        this.file = new File(localDeSalvamentoDoArquivo);
        this.filePath = file.toPath();
        if(!file.exists()){
            this.linkArquivo = geraLinkArquivo();
            this.urlConnection = tentaConexao();
            downloadAPartirDaConexao();
        }
        this.conteudoArquivo = textFileToString();
        if(arquivoValido()) {
            this.conteudoArquivoFormatado = formataConteudoArquivo();
        }
        else{
            //Se a data for maior que a data atual, vai bugar obviamente, bug a ser arrumado
            JsonDataFile jsonDataFile;
            int novoDia,novoMes,novoAno;
            novoMes = mes;
            novoAno = ano;
            if(dia==1){
                if(mes==1){
                    novoDia = 31;
                    novoMes = 12;
                    novoAno = ano-1;
                }
                else if (mes==3){
                    novoDia = 28;
                    novoMes = mes-1;
                }
                else{
                    novoDia = 30;
                    novoMes = mes-1;
                }
            }
            else{
                novoDia = dia-1;
            }
            jsonDataFile = new JsonDataFile(novoDia,novoMes,novoAno);
            this.conteudoArquivo = jsonDataFile.getConteudoArquivo();
            this.conteudoArquivoFormatado = jsonDataFile.getConteudoArquivoFormatado();
            this.url = jsonDataFile.getConteudoArquivoFormatado();
            this.localDeSalvamentoDoArquivo = jsonDataFile.getLocalDeSalvamentoDoArquivo();
            this.linkArquivo = jsonDataFile.getLinkArquivo();
            this.file = jsonDataFile.getFile();
            this.filePath = jsonDataFile.getFilePath();
            this.urlConnection = jsonDataFile.getUrlConnection();
            this.fileInputStream = jsonDataFile.getFileInputStream();
        }
    }

    public String getConteudoArquivo() {
        return conteudoArquivo;
    }

    public void setConteudoArquivo(String conteudoArquivo) {
        this.conteudoArquivo = conteudoArquivo;
    }

    public void setConteudoArquivoFormatado(String conteudoArquivoFormatado) {
        this.conteudoArquivoFormatado = conteudoArquivoFormatado;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalDeSalvamentoDoArquivo() {
        return localDeSalvamentoDoArquivo;
    }

    public void setLocalDeSalvamentoDoArquivo(String localDeSalvamentoDoArquivo) {
        this.localDeSalvamentoDoArquivo = localDeSalvamentoDoArquivo;
    }

    public URL getLinkArquivo() {
        return linkArquivo;
    }

    public void setLinkArquivo(URL linkArquivo) {
        this.linkArquivo = linkArquivo;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public URLConnection getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(URLConnection urlConnection) {
        this.urlConnection = urlConnection;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    private String selecionarURLDeDownload(int dia, int mes, int ano){
        String url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao" +
                "=@dataCotacao)?@dataCotacao='";
        url = url.concat(Integer.toString(mes));
        url = url.concat("-");
        url = url.concat(Integer.toString(dia));
        url = url.concat("-");
        url = url.concat(Integer.toString(ano));
        url = url.concat("'&$top=100&$format=json&$select=cotacaoCompra,cotacaoVenda,dataHoraCotacao");
        return url;
    }

    private String selecionarLocalDeSalvamentoDoArquivo(int dia, int mes, int ano){
        String caminhoBase = Main.pegaCaminhoBase();
        String nomeDoArquivo = "cotacao";
        nomeDoArquivo = nomeDoArquivo.concat(Integer.toString(dia));
        nomeDoArquivo = nomeDoArquivo.concat("-");
        nomeDoArquivo = nomeDoArquivo.concat(Integer.toString((mes)));
        nomeDoArquivo = nomeDoArquivo.concat("-");
        nomeDoArquivo = nomeDoArquivo.concat(Integer.toString(ano));
        nomeDoArquivo = nomeDoArquivo.concat("-");
        Date hora = new Date();
        nomeDoArquivo = nomeDoArquivo.concat(Long.toString(hora.getTime()));
        nomeDoArquivo = nomeDoArquivo.concat(".json");
        String localDeSalvamento = caminhoBase.concat(nomeDoArquivo);
        return localDeSalvamento;
    }

    private URL geraLinkArquivo(){
        URL linkArquivo = null;
        try {
            linkArquivo = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return linkArquivo;
    }

    private URLConnection tentaConexao(){
        try {
            this.urlConnection = linkArquivo.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

    private void abreFluxoDeDadosFTPParaDownload() {
        try {
            fileInputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void baixaDadosArquivo() {
        try {
            Files.copy(fileInputStream, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fechaConexao(){
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadAPartirDaConexao(){
        //System.out.println("Conectando com o servidor de cotação de moedas do banco central brasileiro...");
        abreFluxoDeDadosFTPParaDownload();
        baixaDadosArquivo();
        fechaConexao();
        //System.out.println("Conexão bem sucedida!");
    }

    private boolean arquivoValido(){
        if(conteudoArquivo.contains("[]")){
            //System.out.println("Opa! O dia de hoje não possui cotação específica!");
            return false;
        }
        return true;
    }

    private String formataConteudoArquivo(){
        int valor = conteudoArquivo.indexOf("value");
        String cotacoes = conteudoArquivo.substring(valor-1);
        int valor2 = cotacoes.indexOf("{");
        String correcao2 = cotacoes.substring(valor2);
        String correcao3 = correcao2.substring(0,correcao2.length()-2);
        return correcao3;
    }

    private String textFileToString() throws IOException {
        File file = new File(this.filePath.toString());
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        byte[] byteVector = new byte[(int)file.length()];
        dataInputStream.readFully(byteVector);
        dataInputStream.close();
        fileInputStream.close();
        String text = new String(byteVector);
        //System.out.println(text);
        return text;
    }
}
