package Model;

/**
 *
 * @author Ronny matute
 */
public class clsMusica {

    private String codigo;
    private String nombre;
    private byte[] musica;

    public clsMusica() {
    }

    public clsMusica(String codigo, String nombre, byte[] musica) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.musica = musica;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getMusica() {
        return musica;
    }

    public void setMusica(byte[] musica) {
        this.musica = musica;
    }

    @Override
    public String toString() {
        /*return "----------------------------------------------\n"
                + "Codigo: " + codigo + "\n"
                + "Musica: " + nombre
                + "\n----------------------------------------------";*/
        return nombre;
    }

}
