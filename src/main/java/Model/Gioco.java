package Model;


import java.sql.Date;
import java.util.List;

public class Gioco {

    private String titolo;
    private Boolean offerta;
    private Double prezzo;
    private Date dataUscita;
    private Integer contatoreRilevanze;
    private String genere;
    private String descrizione;

    public Boolean getOfferta() {
        return offerta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    private List<String> pathImages;

    public List<String> getPathImages() {
        return pathImages;
    }

    public void setPathImages(List<String> pathImages) {
        this.pathImages = pathImages;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Boolean isOfferta() {
        return offerta;
    }

    public void setOfferta(Boolean offerta) {
        this.offerta = offerta;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Date getDataUscita() {
        return dataUscita;
    }

    public void setDataUscita(Date dataUscita) {
        this.dataUscita = dataUscita;
    }

    public Integer getContatoreRilevanze() {
        return contatoreRilevanze;
    }

    public void setContatoreRilevanze(Integer contatoreRilevanze) {
        this.contatoreRilevanze = contatoreRilevanze;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String toString() {
        return "Gioco{" +
                "titolo='" + titolo + '\'' +
                ", offerta=" + offerta +
                ", prezzo=" + prezzo +
                ", dataUscita=" + dataUscita +
                ", contatoreRilevanze=" + contatoreRilevanze +
                ", genere='" + genere + '\'' +
                ", pathImages=" + pathImages +
                '}';
    }
}
