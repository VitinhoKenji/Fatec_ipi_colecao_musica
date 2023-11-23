import java.sql.Connection;
import java.sql.DriverManager;
public class TesteConexao{
  public static void main(String[] args) {
    try{
      //string de conexão
      String s = String.format(
        "jdbc:postgresql://localhost:5432/20232_ipi_poo_musicas"
      );
      Connection conexao = DriverManager.getConnection(
        s,
        "postgres",
        "postgres"
      );
      System.out.println(conexao);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}