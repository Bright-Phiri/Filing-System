/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String sql = "SELECT * FROM users WHERE Username = ? AND Password = ? ";
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
        String sql = "INSERT INTO users (FirstName,LastName,Username,Email,Phone,Role,Password) VALUES (?,?,?,?,?,?,?)";
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

    public Boolean addEmployeeFile(Employee employee, String tableName) {
        String sql = "INSERT INTO " + tableName + " (first_name,last_name,department,file_name) VALUES (?,?,?,?)";
        String update = "UPDATE " + tableName + " SET file_name = ? WHERE id = ?";
        String selectFileName = "SELECT file_name from " + tableName + " WHERE id = ?";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getDepartment());
            preparedStatement.setString(4, employee.getFileName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int fileNumber = resultSet.getInt(1);
            String fileName = "";
            resultSet.close();
            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(selectFileName);
            preparedStatement.setInt(1, fileNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fileName = resultSet.getString("file_name");
            }
            fileName = fileName + "" + fileNumber;
            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, fileName);
            preparedStatement.setInt(2, fileNumber);
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

    public Boolean updateEmployee(Employee employee, String tableName) {
        String update = "UPDATE " + tableName + " SET first_name = ?,last_name = ? WHERE id = ?";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getId());
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
        String sql = "UPDATE users SET FirstName = ?,LastName =?,Username = ?,Email = ?,Phone  = ?, Role = ?,Password = ? WHERE Id = ?";
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
        String sql = "SELECT * FROM users WHERE Role = 'Admin'";
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
        String sql = "SELECT COUNT(*) FROM users WHERE Role = 'Admin'";
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
        String sql = "INSERT INTO users (Username,Email,Phone,Role,Password) VALUES (?,?,?,?,?)";
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
        String sql = "UPDATE users SET Username = ?,Email = ?,Phone = ? WHERE Role  = ?";
        try {
            connection = Database.connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, admin.getUserName());
            preparedStatement.setString(2, admin.getEmailAddress());
            preparedStatement.setString(3, admin.getPhone());
            preparedStatement.setString(4, "Admin");
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

    public Boolean validatePassword(int id, String password) {
        String sql = "SELECT * FROM users WHERE Id = ? AND Password  = ?";
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
        String sql = "UPDATE users SET Password = ? WHERE Id = ?";
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
