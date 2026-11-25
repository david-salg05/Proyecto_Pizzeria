
package com.example.proyecto_pizzeria.DAO;

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
}
