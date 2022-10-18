package Model;

public class CartaDiCredito {

    private String numeroCarta;
    private String indirizzo;
    private Integer cap;
    private String citta;
    private String circuito;
    private String dataScadenza;

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String scadenza) {
        this.dataScadenza = scadenza;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    @Override
    public String toString() {
        return "CartaDiCredito{" +
                "numeroCarta='" + numeroCarta + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", cap=" + cap +
                ", citta='" + citta + '\'' +
                '}';
    }
}
