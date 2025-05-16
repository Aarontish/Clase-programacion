package views;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import controllers.UsersController;
import models.User;

public class UsersView {
    private JTable table;
    private DefaultTableModel tableModel;
    private JFrame frame;
    
    public UsersView() {
        initializeTableModel();
    }
    
    private void initializeTableModel() {
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Email", "Usuario", "Rol", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    
    public void index(List<User> usuarios) {
        frame = new JFrame("Gestión de Usuarios");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JButton deleteButton = new JButton("Eliminar Seleccionado");
        deleteButton.addActionListener(this::deleteSelectedUser);
        
        JButton refreshButton = new JButton("Actualizar");
        refreshButton.addActionListener(e -> refreshData());
        
        JButton backButton = new JButton("Regresar");
        backButton.addActionListener(e -> frame.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        refreshData(usuarios);
        
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void deleteSelectedUser(ActionEvent event) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, 
                "Seleccione un usuario para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(frame,
            "¿Está seguro que desea eliminar al usuario: " + name + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            UsersController controller = new UsersController();
            if (controller.deleteUser(id)) {
                JOptionPane.showMessageDialog(frame, 
                    "Usuario eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Error al eliminar el usuario", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshData() {
        UsersController controller = new UsersController();
        refreshData(controller.getUsers());
    }
    
    private void refreshData(List<User> usuarios) {
        tableModel.setRowCount(0);
        
        for (User user : usuarios) {
            tableModel.addRow(new Object[]{
                user.id,
                user.name,
                user.email,
                user.role,
                user.phone,
                user.create_at,
                user.update_at
            });
        }
    }


}