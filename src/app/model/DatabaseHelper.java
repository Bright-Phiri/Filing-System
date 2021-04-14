/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bright
 */
public class DatabaseHelper {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    FileInputStream fis;

    public static String USERTYPE;
    public static String USERNAME;
    public static int ID;

    public Boolean userSignIn(String userName, String password) {
        String sql = "SELECT * FROM User WHERE Username = ? AND Password = ? ";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ID = resultSet.getInt(1);
                USERNAME = resultSet.getString(4);
                USERTYPE = resultSet.getString(7);
                return Boolean.TRUE;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Boolean.FALSE;
    }

    public Boolean addUser(User user) {
        String sql = "INSERT INTO User (FirstName,LastName,Username,Email,Phone,Role,Password) VALUES (?,?,?,?,?,?,?)";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmailAddress());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getUserType());
            preparedStatement.setString(7, user.getPassword());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            return Boolean.FALSE;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean updateUser(User user) {
        String sql = "UPDATE User SET FirstName = ?,LastName =?,Username = ?,Email = ?,Phone  = ?, Role = ?,Password = ? WHERE Id = ?";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmailAddress());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getUserType());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setInt(8, user.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            return Boolean.FALSE;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean ifAdminExists() {
        String sql = "SELECT * FROM User WHERE Role = 'Admin'";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Boolean.TRUE;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Boolean.FALSE;
    }

    public int ifRecords() {
        int numberOfRecords = 0;
        String sql = "SELECT COUNT(*) FROM User WHERE Role = 'Admin'";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            numberOfRecords = resultSet.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return numberOfRecords;
    }

    public Boolean createAccount(Admin admin) {
        String sql = "INSERT INTO User (Username,Email,Phone,Role,Password) VALUES (?,?,?,?,?)";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, admin.getUserName());
            preparedStatement.setString(2, admin.getEmailAddress());
            preparedStatement.setString(3, admin.getPhone());
            preparedStatement.setString(4, "Admin");
            preparedStatement.setString(5, admin.getPassword());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            return Boolean.FALSE;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean updateAdmin(Admin admin) {
        String sql = "UPDATE User SET Username = ?,Email = ?,Phone = ? WHERE Role  = ?";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, admin.getUserName());
            preparedStatement.setString(2, admin.getEmailAddress());
            preparedStatement.setString(3, admin.getPhone());
            preparedStatement.setString(4, "Admin");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException  ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            return Boolean.FALSE;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean validatePassword(int id, String password) {
        String sql = "SELECT * FROM User WHERE Id = ? AND Password  = ?";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Boolean.TRUE;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Boolean.FALSE;
    }

    public Boolean updatePassword(int id, String passwrd) {
        String sql = "UPDATE User SET Password = ? WHERE Id = ?";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, passwrd);
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Boolean.FALSE;
    }

}
