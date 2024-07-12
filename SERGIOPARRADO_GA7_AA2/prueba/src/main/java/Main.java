import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/evidencia_ga7_aa2";
    private static final String USER = "root";  // Cambia 'root' si usas otro usuario
    private static final String PASSWORD = "";  // Añade tu contraseña si tienes una

    public static void main(String[] args) {
        // Prueba de las funcionalidades
        insertUsuario("Jorge", "jorge@example.com", "password123");
        insertUsuario("Bob", "bob@example.com", "password456");

        getUsuarios();

        updateUsuario(1, "Jorge Updated", "jorge.updated@example.com", "newpassword123");
        getUsuarios();

        deleteUsuario(2);
        getUsuarios();
    }

    // Método para insertar un usuario
    public static void insertUsuario(String nombre, String email, String contraseña) {
        String insertSQL = "INSERT INTO usuarios (nombre, email, contraseña) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, contraseña);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para consultar todos los usuarios
    public static void getUsuarios() {
        String selectSQL = "SELECT * FROM usuarios";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("nombre");
                String email = resultSet.getString("email");
                String contraseña = resultSet.getString("contraseña");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Email: " + email + ", Contraseña: " + contraseña);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar un usuario
    public static void updateUsuario(int id, String nombre, String email, String contraseña) {
        String updateSQL = "UPDATE usuarios SET nombre = ?, email = ?, contraseña = ? WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, contraseña);
            preparedStatement.setInt(4, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Updated " + rowsAffected + " row(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un usuario
    public static void deleteUsuario(int id) {
        String deleteSQL = "DELETE FROM usuarios WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " row(s)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
