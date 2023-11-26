import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MusicaDAO {

  public void cadastrar(Musica musica) throws Exception {
    String sql = "INSERT INTO tb_musica(titulo, ativo) VALUES(?, true)";
    var conexao = ConnectionFactory.conectar();
    PreparedStatement ps = conexao.prepareStatement(sql);
    ps.setString(1, musica.getTitulo());
    ps.execute();
    ps.close();
    conexao.close();
  }

  public void avaliar(Musica musica) throws Exception {
    var sql = "UPDATE tb_musica SET avaliacao=? WHERE titulo=?;";
    try (
        var conexao = ConnectionFactory.conectar();
        var ps = conexao.prepareStatement(sql);) {
      ps.setInt(1, musica.getAvaliacao());
      ps.setString(2, musica.getTitulo());
      ps.execute();
    }
  }

  public ArrayList<Musica> listar() throws Exception {
    var sql = "SELECT titulo, avaliacao FROM tb_musica where ativo = true";
    var musicas = new ArrayList<Musica>();

    try (
        var conexao = ConnectionFactory.conectar();
        var ps = conexao.prepareStatement(sql);

    ) {
      try (
          ResultSet rs = ps.executeQuery();) {
        while (rs.next()) {
          int avaliacao = rs.getInt("avaliacao");
          String titulo = rs.getString("titulo");
          var musica = new Musica(titulo, avaliacao);
          musicas.add(musica);
        }
      }
    }
    musicas.sort(new ComparadorAvaliacao());
    return musicas;
  }

  public void desativar(String titulo) throws Exception {
    var sql = "UPDATE tb_musica SET ativo = false WHERE titulo =?;";

    try (
        var conexao = ConnectionFactory.conectar();
        var ps = conexao.prepareStatement(sql);) {
      ps.setString(1, titulo);
      ps.execute();
    }
  }
}
