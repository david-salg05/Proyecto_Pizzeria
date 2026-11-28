
package com.example.proyecto_pizzeria.DAO;

import com.example.proyecto_pizzeria.entity.Pedido;
import com.example.proyecto_pizzeria.entity.Pizza;
import com.example.proyecto_pizzeria.entity.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    private static ConexionBD conexionBD = new ConexionBD();

    //Funcion que comprueba en la bd si existe un usuario con esa contraseña enviada como parametro
    public static boolean consultarUsuario(String usu, String contr) throws SQLException {
        String sql = "select * from usuarios where usuario = ? and password = ?;";

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, usu);
        ps.setString(2, contr);

        ResultSet rs = ps.executeQuery();

        boolean existe = rs.next();

        rs.close();
        ps.close();
        con.close();

        return existe;
    }

    public static String comprobarTipo(String usu, String contr) throws SQLException {
        String sql = "select * from usuarios where usuario = ? and password = ?;";

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, usu);
        ps.setString(2, contr);

        ResultSet rs = ps.executeQuery();

        String ti = "";

        if(rs.next()){
            ti = rs.getString("tipo");
        }

        rs.close();
        ps.close();
        con.close();

        return ti;
    }

    //Funcion que registra un cliente en la bd
    public static boolean registrarCliente(String nom, String usu, String corr, String con1) throws SQLException {
        String sql = "insert into usuarios(nombre, usuario, email, password, tipo) values (?,?,?,?,?);";

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, nom);
        ps.setString(2, usu);
        ps.setString(3, corr);
        ps.setString(4, con1);
        ps.setString(5, "Cliente");

        int res = ps.executeUpdate();

        ps.close();

        return res > 0;
    }

    //Funcion que devuelve una lista de clientes registrados en la bd
    public static ArrayList<Usuario> devolverClientes() throws SQLException {
        String sql = "select * from usuarios where tipo = 'cliente';";

        ArrayList<Usuario> listaClientes = new ArrayList<>();

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Usuario us = new Usuario();

            us.setNombre(rs.getString("nombre"));
            us.setUsuario(rs.getString("usuario"));
            us.setEmail(rs.getString("email"));

            listaClientes.add(us);
        }

        ps.close();

        return listaClientes;
    }

    //Funcion que devuelve los clientes con el nombre indicado
    public static ArrayList<Usuario> buscarClientes(String texto) throws SQLException {
        String sql = "select * from usuarios where tipo = 'cliente' and LOWER(nombre) LIKE LOWER(?);";

        ArrayList<Usuario> listaClientes = new ArrayList<>();

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "%" + texto + "%");

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Usuario us = new Usuario();

            us.setNombre(rs.getString("nombre"));
            us.setUsuario(rs.getString("usuario"));
            us.setEmail(rs.getString("email"));

            listaClientes.add(us);
        }

        rs.close();
        ps.close();
        con.close();

        return listaClientes;
    }

    // Funcion que elimina un cliente de la bd
    public static boolean eliminarCliente(String usu) throws SQLException {

        String sql = "DELETE FROM usuarios WHERE usuario = ? AND tipo = 'cliente';";

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, usu);

        int res = ps.executeUpdate();

        ps.close();
        con.close();

        return res > 0;
    }

    //Funcion que devuelve unicamente los 5 clientes con mas pedidos
    public static ArrayList<Usuario> devolverClientesTop() throws SQLException {
        String sql = "select usuarios.nombre, count(pedidos.id) as total_pedidos from usuarios join pedidos on usuarios.id = pedidos.id_usuario group by usuarios.id, usuarios.nombre order by total_pedidos desc limit 5;";

        ArrayList<Usuario> listaClientes = new ArrayList<>();

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Usuario us = new Usuario();

            us.setNombre(rs.getString("nombre"));
            us.setTotal_pedidos(rs.getInt("total_pedidos"));

            listaClientes.add(us);
        }

        ps.close();

        return listaClientes;
    }

    // Funcion que devuelve todas las pizzas y el numero de pedidos de cada una
    public static ArrayList<Pizza> devolverPizzas() throws SQLException {

        String sql = "SELECT pizzas.*, COUNT(pedidos.id) AS stock FROM pizzas LEFT JOIN pedidos ON pizzas.id = pedidos.id_pizza GROUP BY pizzas.id;";

        ArrayList<Pizza> listaPizzas = new ArrayList<>();

        Connection con = conexionBD.abrirConexion();

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Pizza p = new Pizza();

            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setPrecio(rs.getDouble("precio"));
            p.setImagen(rs.getString("imagen"));
            p.setActiva(rs.getBoolean("activa"));
            p.setStock(rs.getInt("stock"));

            listaPizzas.add(p);
        }

        rs.close();
        ps.close();
        con.close();

        return listaPizzas;
    }

    //Funcion que anhade una nueva pizza a la bd
    public static boolean crearPizza(String nom, String descrip, String img, String pr) throws SQLException {
        String sql = "INSERT INTO pizzas (nombre, descripcion, precio, imagen) VALUES (?, ?, ?, ?)";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, nom);
        ps.setString(2, descrip);
        ps.setDouble(3, Double.parseDouble(pr));
        ps.setString(4, img);

        int resultado = ps.executeUpdate();

        ps.close();
        con.close();

        return resultado > 0;
    }

    //Funcion que da de baja una pizza
    public static boolean darDeBajaPizza(int id) throws SQLException {
        String sql = "UPDATE pizzas SET activa = FALSE WHERE id = ?";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        int resultado = ps.executeUpdate();

        ps.close();
        con.close();

        return resultado > 0;
    }

    // Funcion que devuelve una pizza por su ID
    public static Pizza devolverPizza(int id) throws SQLException {

        String sql = "SELECT * FROM pizzas WHERE id = ?";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Pizza pizza = null;

        if (rs.next()) {
            pizza = new Pizza();

            pizza.setId(rs.getInt("id"));
            pizza.setNombre(rs.getString("nombre"));
            pizza.setDescripcion(rs.getString("descripcion"));
            pizza.setPrecio(rs.getDouble("precio"));
            pizza.setImagen(rs.getString("imagen"));
            pizza.setActiva(rs.getBoolean("activa"));
        }

        rs.close();
        ps.close();
        con.close();

        return pizza;
    }

    //Funcion que actualiza la informacion de una pizza en la bd
    public static boolean modificarPizza(int id, String nom, String descrip, String img, String pr) throws SQLException {

        String sql = "UPDATE pizzas SET nombre = ?, descripcion = ?, precio = ?, imagen = ? WHERE id = ?";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, nom);
        ps.setString(2, descrip);
        ps.setDouble(3, Double.parseDouble(pr));
        ps.setString(4, img);
        ps.setInt(5, id);

        int resultado = ps.executeUpdate();

        ps.close();
        con.close();

        return resultado > 0;
    }

    //Funcion que devuelve todos los pedidos realizados
    public static ArrayList<Pedido> devolverPedidos() throws SQLException {

        String sql = "SELECT pedidos.*, usuarios.nombre AS cliente, pizzas.nombre AS pizzas " +
                "FROM pedidos " +
                "INNER JOIN usuarios ON pedidos.id_usuario = usuarios.id " +
                "INNER JOIN pizzas ON pedidos.id_pizza = pizzas.id " +
                "ORDER BY pedidos.fecha DESC";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Pedido> lista = new ArrayList<>();

        while (rs.next()) {

            Pedido pedido = new Pedido(
                    rs.getInt("id"),
                    rs.getString("cliente"),
                    rs.getString("fecha"),
                    rs.getString("pizzas"),
                    rs.getString("estado")
            );

            lista.add(pedido);
        }

        rs.close();
        ps.close();
        con.close();

        return lista;
    }

    //Funcion que modifica el estado de un pedido
    public static boolean modificarEstadoPedido(int id, String estado) throws SQLException {

        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, estado);
        ps.setInt(2, id);

        int resultado = ps.executeUpdate();

        ps.close();
        con.close();

        return resultado > 0;
    }

    //Funcion que cuenta el numero de pizzas pisponibles en carta
    public static int contarPizzasDisponibles() throws SQLException {

        String sql = "SELECT COUNT(*) FROM pizzas WHERE activa = TRUE";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int resultado = 0;

        if (rs.next()) {
            resultado = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

        return resultado;
    }

    //Funcion que cuenta el numero de clientes registrados
    public static int contarClientes() throws SQLException {

        String sql = "SELECT COUNT(*) FROM usuarios WHERE tipo = 'cliente'";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int resultado = 0;

        if (rs.next()) {
            resultado = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

        return resultado;
    }

    //Funcion que cuenta el numero de pedidos
    public static int contarPedidos() throws SQLException {

        String sql = "SELECT COUNT(*) FROM pedidos";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int resultado = 0;

        if (rs.next()) {
            resultado = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

        return resultado;
    }

    //Funcion que cuenta el dinero recaudado
    public static double dineroDisponible() throws SQLException {

        String sql = "SELECT COALESCE(SUM(importe), 0) FROM pedidos";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        double resultado = 0;

        if (rs.next()) {
            resultado = rs.getDouble(1);
        }

        rs.close();
        ps.close();
        con.close();

        return resultado;
    }

    // Funcion que devuelve los datos de un usuario
    public static Usuario devolverUsuario(String usu, String contr) throws SQLException {

        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, usu);
        ps.setString(2, contr);

        ResultSet rs = ps.executeQuery();

        Usuario us = null;

        if (rs.next()) {
            us = new Usuario();

            us.setNombre(rs.getString("nombre"));
            us.setUsuario(rs.getString("usuario"));
            us.setEmail(rs.getString("email"));
            us.setPassword(rs.getString("password"));
            us.setTipo(rs.getString("tipo"));
            us.setActivo(rs.getBoolean("activo"));
        }

        rs.close();
        ps.close();
        con.close();

        return us;
    }

    // Funcion que modifica los datos personales del propietario
    public static boolean modificarDatosPropietario(String usuarioAnterior, String nom, String usu, String corr, String contr) throws SQLException {

        String sql = "UPDATE usuarios SET nombre = ?, usuario = ?, email = ?, password = ? WHERE usuario = ? AND tipo = 'propietario'";

        Connection con = conexionBD.abrirConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, nom);
        ps.setString(2, usu);
        ps.setString(3, corr);
        ps.setString(4, contr);
        ps.setString(5, usuarioAnterior);

        int resultado = ps.executeUpdate();

        ps.close();
        con.close();

        return resultado > 0;
    }
}
